package com.parris.yotolite

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
import com.parris.yotolite.backup.BackupManager
import com.parris.yotolite.data.AppDatabase
import kotlinx.coroutines.launch

class CardsFragment : Fragment() {
    private lateinit var viewModel: CardsViewModel
    private lateinit var tvCards: TextView

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
        tvCards = v.findViewById(R.id.tvCards)
        val btnCreate = v.findViewById<Button>(R.id.btnCreateCard)
        val btnExport = v.findViewById<Button>(R.id.btnExport)
        val btnImport = v.findViewById<Button>(R.id.btnImport)

        btnCreate.setOnClickListener {
            btnCreate.isEnabled = false
            Thread {
                val db = AppDatabase.getInstance(requireContext())
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

        btnExport.setOnClickListener {
            handleExport()
        }

        btnImport.setOnClickListener {
            importLauncher.launch("application/zip")
        }

        return v
    }

    private fun handleExport() {
        viewLifecycleOwner.lifecycleScope.launch {
            val db = AppDatabase.getInstance(requireContext())
            val zipUri = BackupManager.exportToZip(requireContext(), db)

            if (zipUri != null) {
                tvCards.text = "Export successful!\n$zipUri"
                Toast.makeText(requireContext(), "Backup created in cache", Toast.LENGTH_SHORT).show()
            } else {
                tvCards.text = "Export failed"
                Toast.makeText(requireContext(), "Export failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleImport(uri: android.net.Uri) {
        viewLifecycleOwner.lifecycleScope.launch {
            val db = AppDatabase.getInstance(requireContext())
            val success = BackupManager.importFromZip(requireContext(), uri, db)

            if (success) {
                tvCards.text = "Import successful!"
                Toast.makeText(requireContext(), "Cards and tracks imported", Toast.LENGTH_SHORT).show()
            } else {
                tvCards.text = "Import failed"
                Toast.makeText(requireContext(), "Import failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
