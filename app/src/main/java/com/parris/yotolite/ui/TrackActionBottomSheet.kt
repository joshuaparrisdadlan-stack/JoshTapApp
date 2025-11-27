package com.parris.yotolite.ui

import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.parris.yotolite.R
import com.parris.yotolite.PlayerController
import com.parris.yotolite.data.AppDatabase
import com.parris.yotolite.data.CardEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
            // show list of cards to add to
            CoroutineScope(Dispatchers.Main).launch {
                val db = AppDatabase.getInstance(requireContext())
                val cards = db.appDao().listCards()
                if (cards.isEmpty()) {
                    AlertDialog.Builder(requireContext())
                        .setMessage("No cards available. Create a card first in Cards tab.")
                        .setPositiveButton("OK", null)
                        .show()
                } else {
                    val names = cards.map { it.name + " (" + it.token + ")" }.toTypedArray()
                    AlertDialog.Builder(requireContext())
                        .setTitle("Add to card")
                        .setItems(names) { _, which ->
                            val card = cards[which]
                            // perform add
                            CoroutineScope(Dispatchers.IO).launch {
                                val repo = com.parris.yotolite.data.AppRepository(db)
                                repo.addTrackToCard(card.id, trackId)
                                // notify on main
                                CoroutineScope(Dispatchers.Main).launch {
                                    _onAdded?.invoke(card.id)
                                }
                            }
                        }
                        .show()
                }
            }
            dismiss()
        }

        return v
    }
}
