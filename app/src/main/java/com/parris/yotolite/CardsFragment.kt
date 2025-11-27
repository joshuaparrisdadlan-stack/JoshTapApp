package com.parris.yotolite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class CardsFragment : Fragment() {
    private lateinit var viewModel: CardsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = CardsViewModel(requireActivity().application)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_cards, container, false)
        val btnCreate = v.findViewById<Button>(R.id.btnCreateCard)
        val tvCards = v.findViewById<TextView>(R.id.tvCards)

        btnCreate.setOnClickListener {
            btnCreate.isEnabled = false
            // For demo: create a card assigned to the first track if exists
            // Simple approach: look up tracks via DB directly
            Thread {
                val db = com.parris.yotolite.data.AppDatabase.getInstance(requireContext())
                val tracks = db.appDao().listTracks()
                if (tracks.isNotEmpty()) {
                    val first = tracks[0]
                    requireActivity().runOnUiThread {
                        viewModel.createCardWithTrack("Demo Card", first.id) { cardId, token ->
                            tvCards.text = "Created card id=$cardId token=$token"
                            btnCreate.isEnabled = true
                        }
                    }
                } else {
                    requireActivity().runOnUiThread {
                        tvCards.text = "No tracks available to assign. Import demo first."
                        btnCreate.isEnabled = true
                    }
                }
            }.start()
        }

        return v
    }
}
