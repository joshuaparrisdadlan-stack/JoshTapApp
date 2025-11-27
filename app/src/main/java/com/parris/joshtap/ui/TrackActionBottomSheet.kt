package com.parris.joshtap.ui

import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.parris.joshtap.R
import com.parris.joshtap.PlayerController
import com.parris.joshtap.data.AppDatabase
import com.parris.joshtap.data.CardEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TrackActionBottomSheet : BottomSheetDialogFragment() {
    private var title: String? = null
    private var uriString: String? = null
    private var trackId: Long = 0L

    companion object {
        private const val ARG_TITLE = "title"
        private const val ARG_URI = "uri"
        private const val ARG_TRACK_ID = "track_id"

        fun show(context: Context, fm: androidx.fragment.app.FragmentManager, title: String, uri: String, trackId: Long, onAdded: ((Long)->Unit)? = null) {
            val bs = TrackActionBottomSheet()
            val args = Bundle()
            args.putString(ARG_TITLE, title)
            args.putString(ARG_URI, uri)
            args.putLong(ARG_TRACK_ID, trackId)
            bs.arguments = args
            bs._onAdded = onAdded
            bs.show(fm, "track_actions")
        }
    }

    private var _onAdded: ((Long)->Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            title = it.getString(ARG_TITLE)
            uriString = it.getString(ARG_URI)
            trackId = it.getLong(ARG_TRACK_ID)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.bs_track_actions, container, false)
        val tv = v.findViewById<TextView>(R.id.tvTrackTitle)
        val btnPlay = v.findViewById<Button>(R.id.btnPlayPreview)
        val btnAdd = v.findViewById<Button>(R.id.btnAddToCard)

        tv.text = title ?: "Track"

        btnPlay.setOnClickListener {
            uriString?.let { u ->
                try {
                    PlayerController.initialize(requireContext())
                    PlayerController.playTracks(listOf(Uri.parse(u)))
                } catch (e: Exception) {
                    // ignore play errors
                }
            }
            dismiss()
        }

        btnAdd.setOnClickListener {
            // show list of cards to add to — do DB IO off main thread
            lifecycleScope.launch {
                val db = try {
                    AppDatabase.getInstance(requireContext())
                } catch (e: Exception) {
                    AlertDialog.Builder(requireContext())
                        .setMessage("Unable to access database: ${e.message}")
                        .setPositiveButton("OK", null)
                        .show()
                    return@launch
                }

                val cards = try {
                    withContext(Dispatchers.IO) { db.appDao().listCards() }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        AlertDialog.Builder(requireContext())
                            .setMessage("Failed to load cards: ${e.message}")
                            .setPositiveButton("OK", null)
                            .show()
                    }
                    return@launch
                }

                if (cards.isEmpty()) {
                    AlertDialog.Builder(requireContext())
                        .setMessage("No cards available. Create a card first in Cards tab.")
                        .setPositiveButton("OK", null)
                        .show()
                    return@launch
                }

                val names = cards.map { it.name + " (" + it.token + ")" }.toTypedArray()
                AlertDialog.Builder(requireContext())
                    .setTitle("Add to card")
                    .setItems(names) { _, which ->
                        // guard index
                        if (which < 0 || which >= cards.size) return@setItems
                        val card = cards[which]
                        // perform add on IO dispatcher
                        lifecycleScope.launch {
                            try {
                                withContext(Dispatchers.IO) {
                                    val repo = com.parris.joshtap.data.AppRepository(db)
                                    repo.addTrackToCard(card.id, trackId)
                                }
                                // notify on main thread
                                withContext(Dispatchers.Main) {
                                    try {
                                        _onAdded?.invoke(card.id)
                                    } catch (e: Exception) {
                                        // swallow callback errors
                                    }

                                    // show confirmation using activity root view if available
                                    val root = activity?.findViewById<View>(android.R.id.content)
                                    if (root != null) {
                                        com.google.android.material.snackbar.Snackbar
                                            .make(root, "Added to ${card.name}", com.google.android.material.snackbar.Snackbar.LENGTH_SHORT)
                                            .show()
                                    }
                                }
                            } catch (e: Exception) {
                                withContext(Dispatchers.Main) {
                                    AlertDialog.Builder(requireContext())
                                        .setMessage("Failed to add track to card: ${e.message}")
                                        .setPositiveButton("OK", null)
                                        .show()
                                }
                            }
                        }
                    }
                    .show()
            }

            dismiss()
        }

        return v
    }
}
