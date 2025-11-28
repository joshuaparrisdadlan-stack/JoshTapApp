package com.parris.joshtap

import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.os.Bundle
import com.parris.joshtap.nfc.NfcHandler
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.ViewModelProvider
import com.parris.joshtap.nfc.NfcViewModel
import com.parris.joshtap.nfc.NfcViewModelFactory
import com.google.android.material.snackbar.Snackbar
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.parris.joshtap.data.AppDatabase
import com.parris.joshtap.data.TrackEntity
import com.parris.joshtap.data.AppRepository
import com.parris.joshtap.ui.CardTracksAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CardDetailActivity : AppCompatActivity() {
    private var cardId: Long = -1L
    // WRITE_NFC_REQUEST removed — writing handled by NfcViewModel + foreground dispatch
    private lateinit var tvCardTitle: TextView
    private lateinit var tvCardToken: TextView
    private lateinit var tvTrackCount: TextView
    private lateinit var rvAssigned: RecyclerView
    private lateinit var btnAdd: Button
    private lateinit var btnSave: Button
    private lateinit var fabWriteNfc: Button
    private lateinit var adapter: CardTracksAdapter
    private lateinit var touchHelper: ItemTouchHelper
    private data class RemovedTrack(val track: TrackEntity, val index: Int)
    private var lastRemoved: RemovedTrack? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_detail)

        cardId = intent?.getLongExtra("cardId", -1L) ?: -1L

        tvCardTitle = findViewById(R.id.tvCardTitle)
        tvCardToken = findViewById(R.id.tvCardToken)
        rvAssigned = findViewById(R.id.rvAssignedTracks)
        btnAdd = findViewById(R.id.btnAddTracks)
        btnSave = findViewById(R.id.btnSave)

        rvAssigned.layoutManager = LinearLayoutManager(this)

        // attach ItemTouchHelper for drag & drop; adapter will call back to start drag
        val touchCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            0
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val from = viewHolder.bindingAdapterPosition
                val to = target.bindingAdapterPosition
                adapter.moveItem(from, to)
                updateTrackCount()
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // no-op for swipe; removal handled via button
            }
        }
        touchHelper = ItemTouchHelper(touchCallback)
        touchHelper.attachToRecyclerView(rvAssigned)

        // create adapter with removal delegated to activity and handle-only drag start
        adapter = CardTracksAdapter(mutableListOf(), onRemoveClick = { track, pos ->
            // record removal for possible undo
            lastRemoved = RemovedTrack(track, pos)
            adapter.removeAt(pos)
            updateTrackCount()

            val parentView: View = findViewById(android.R.id.content)
            Snackbar.make(parentView, "Track removed", Snackbar.LENGTH_LONG)
                .setAction("UNDO") {
                    val removed = lastRemoved
                    if (removed != null) {
                        adapter.insertAt(removed.index, removed.track)
                        updateTrackCount()
                        lastRemoved = null
                    }
                }
                .show()

        }, onStartDrag = { vh -> touchHelper.startDrag(vh) })
        rvAssigned.adapter = adapter

        btnAdd.setOnClickListener { showAddDialog() }
        btnSave.setOnClickListener { saveAndFinish() }

        fabWriteNfc = findViewById(R.id.fabWriteNfc)
        val db = AppDatabase.getInstance(this)
        val repo = AppRepository(db)
        val nfcVm = ViewModelProvider(this, com.parris.joshtap.nfc.NfcViewModelFactory(repo)).get(NfcViewModel::class.java)

        fabWriteNfc.setOnClickListener {
            // confirm then start write mode via ViewModel
            val name = tvCardTitle.text?.toString() ?: "Card"
            val tokenText = tvCardToken.text?.toString()?.removePrefix("token: ") ?: ""
            androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Write to NFC Card")
                .setMessage("Write '$name' (token: $tokenText) to NFC tag?")
                .setPositiveButton("Write") { _, _ ->
                    // start write mode; CardDetail has cardId
                    nfcVm.startWrite(cardId)
                    Snackbar.make(findViewById(android.R.id.content), "Tap a card to write this playlist.", Snackbar.LENGTH_LONG).show()
                }
                .setNegativeButton("Cancel", null)
                .show()
        }

        // Observe NFC state for write success/error
        lifecycleScope.launchWhenStarted {
            nfcVm.state.collect { st ->
                when (st) {
                    is com.parris.joshtap.nfc.NfcUiState.WriteSuccess -> {
                        Snackbar.make(findViewById(android.R.id.content), "NFC card written successfully!", Snackbar.LENGTH_LONG).show()
                        nfcVm.reset()
                    }
                    is com.parris.joshtap.nfc.NfcUiState.Error -> {
                        Snackbar.make(findViewById(android.R.id.content), "NFC Error: ${st.message}", Snackbar.LENGTH_LONG).show()
                        nfcVm.reset()
                    }
                    else -> {}
                }
            }
        }

        loadCard()
    }

    private fun loadCard() {
        lifecycleScope.launch {
            val db = AppDatabase.getInstance(this@CardDetailActivity)
            val cw = withContext(Dispatchers.IO) { db.appDao().getCardWithTracksById(cardId) }
            if (cw != null) {
                tvCardTitle.text = cw.card.name
                tvCardToken.text = "token: ${cw.card.token}"
                adapter.submitList(cw.tracks.toMutableList())
                updateTrackCount()
            } else {
                adapter.submitList(emptyList())
            }
        }
    }

    private fun updateTrackCount() {
        val count = adapter.getOrderedIds().size
        tvTrackCount.text = "$count ${if (count == 1) "track" else "tracks"}"
    }

    private fun showAddDialog() {
        lifecycleScope.launch {
            val db = AppDatabase.getInstance(this@CardDetailActivity)
            val all = withContext(Dispatchers.IO) { db.appDao().listTracks() }
            val assignedIds = adapter.getOrderedIds().toSet()
            val candidates = all.filter { it.id !in assignedIds }

            if (candidates.isEmpty()) {
                Toast.makeText(this@CardDetailActivity, "No available tracks to add", Toast.LENGTH_SHORT).show()
                return@launch
            }

            // simple multi-select dialog using AndroidX AlertDialog with checkboxes
            val names = candidates.map { it.displayName ?: it.localUri.toString() }.toTypedArray()
            val checked = BooleanArray(names.size)
            val selected = mutableListOf<Int>()

            androidx.appcompat.app.AlertDialog.Builder(this@CardDetailActivity)
                .setTitle("Select tracks to add")
                .setMultiChoiceItems(names, checked) { _, which, isChecked ->
                    if (isChecked) selected.add(which) else selected.remove(which)
                }
                .setPositiveButton("Add Selected") { _, _ ->
                    val toAdd = selected.map { candidates[it] }
                    lifecycleScope.launch {
                        val db2 = AppDatabase.getInstance(this@CardDetailActivity)
                        val cw = withContext(Dispatchers.IO) { db2.appDao().getCardWithTracksById(cardId) }
                        val merged = mutableListOf<TrackEntity>()
                        if (cw != null) merged.addAll(cw.tracks)
                        merged.addAll(toAdd)
                        adapter.submitList(merged)
                    }
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }

    private fun saveAndFinish() {
        lifecycleScope.launch {
            val db = AppDatabase.getInstance(this@CardDetailActivity)
            val repo = AppRepository(db)
            val ordered = adapter.getOrderedIds()
            withContext(Dispatchers.IO) {
                repo.setCardTracks(cardId, ordered)
            }
            Toast.makeText(this@CardDetailActivity, "Changes saved", Toast.LENGTH_SHORT).show()
            setResult(RESULT_OK)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        try {
            val filter = arrayOf(IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED))
            NfcHandler.enableForegroundDispatch(this, this, filter, arrayOf(arrayOf("android.nfc.tech.Ndef")))
        } catch (ex: Exception) {
            // ignore
        }
    }

    override fun onPause() {
        super.onPause()
        try {
            NfcHandler.disableForegroundDispatch(this, this)
        } catch (ex: Exception) {
            // ignore
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (intent.action == NfcAdapter.ACTION_TAG_DISCOVERED) {
            val tag: android.nfc.Tag? = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)
            if (tag != null) {
                // forward to local activity-scoped viewmodel
                val db = AppDatabase.getInstance(this)
                val repo = AppRepository(db)
                val nvm = ViewModelProvider(this, NfcViewModelFactory(repo)).get(NfcViewModel::class.java)
                nvm.onTag(tag)
            }
        }
    }
}
