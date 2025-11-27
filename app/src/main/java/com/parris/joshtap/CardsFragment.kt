package com.parris.joshtap

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.parris.joshtap.backup.BackupManager
import com.parris.joshtap.data.AppDatabase
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.parris.joshtap.ui.CardAdapter
import kotlinx.coroutines.launch

class CardsFragment : Fragment() {
    private lateinit var viewModel: CardsViewModel
    private lateinit var tvCardsEmpty: TextView
    private var rvCards: RecyclerView? = null
    private lateinit var cardAdapter: CardAdapter

    private val importLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            handleImport(uri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = CardsViewModel(requireActivity().application)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_cards, container, false)
        rvCards = v.findViewById(R.id.rvCards)
        tvCardsEmpty = v.findViewById(R.id.tvCardsEmpty)
        cardAdapter = CardAdapter(onItemClick = { card ->
            val intent = Intent(requireContext(), CardDetailActivity::class.java)
            intent.putExtra("cardId", card.id)
            startActivity(intent)
        })
        rvCards?.layoutManager = LinearLayoutManager(requireContext())
        rvCards?.adapter = cardAdapter
        val btnCreate = v.findViewById<Button>(R.id.btnCreateCard)
        val btnExport = v.findViewById<Button>(R.id.btnExport)
        val btnImport = v.findViewById<Button>(R.id.btnImport)

        btnCreate.setOnClickListener {
            btnCreate.isEnabled = false
            viewLifecycleOwner.lifecycleScope.launch {
                val db = AppDatabase.getInstance(requireContext())
                val tracks = db.appDao().listTracks()
                if (tracks.isNotEmpty()) {
                    val first = tracks[0]
                    viewModel.createCardWithTrack("Demo Card", first.id) { cardId, token ->
                        // refresh list
                        viewModel.loadCards()
                        tvCardsEmpty.visibility = View.GONE
                        rvCards?.visibility = View.VISIBLE
                        btnCreate.isEnabled = true
                    }
                } else {
                    tvCardsEmpty.text = "No tracks available to assign. Import demo first."
                    tvCardsEmpty.visibility = View.VISIBLE
                    rvCards?.visibility = View.GONE
                    btnCreate.isEnabled = true
                }
            }
        }

        btnExport.setOnClickListener {
            handleExport()
        }

        btnImport.setOnClickListener {
            importLauncher.launch("application/zip")
        }

        // Observe cards from ViewModel and update RecyclerView
        viewModel.cards.observe(viewLifecycleOwner) { list ->
            if (list.isNullOrEmpty()) {
                tvCardsEmpty.text = "No cards"
                tvCardsEmpty.visibility = View.VISIBLE
                rvCards?.visibility = View.GONE
            } else {
                tvCardsEmpty.visibility = View.GONE
                rvCards?.visibility = View.VISIBLE
                cardAdapter.submitList(list)
            }
        }

        // item clicks handled via adapter's onItemClick lambda

        viewModel.loadCards()

        return v
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadCards()
    }

    private fun handleExport() {
        viewLifecycleOwner.lifecycleScope.launch {
            val db = AppDatabase.getInstance(requireContext())
            val zipUri = BackupManager.exportToZip(requireContext(), db)

            if (zipUri != null) {
                tvCardsEmpty.text = "Export successful!\n$zipUri"
                Toast.makeText(requireContext(), "Backup created in cache", Toast.LENGTH_SHORT).show()
            } else {
                tvCardsEmpty.text = "Export failed"
                Toast.makeText(requireContext(), "Export failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleImport(uri: android.net.Uri) {
        viewLifecycleOwner.lifecycleScope.launch {
            val db = AppDatabase.getInstance(requireContext())
            val success = BackupManager.importFromZip(requireContext(), uri, db)

            if (success) {
                tvCardsEmpty.text = "Import successful!"
                Toast.makeText(requireContext(), "Cards and tracks imported", Toast.LENGTH_SHORT).show()
            } else {
                tvCardsEmpty.text = "Import failed"
                Toast.makeText(requireContext(), "Import failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
