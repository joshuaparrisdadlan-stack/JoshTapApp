package com.parris.yotolite

import android.content.Intent
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.parris.yotolite.ui.TrackAdapter
import kotlinx.coroutines.launch

class LibraryFragment : Fragment() {
    private lateinit var viewModel: LibraryViewModel

    private val pickAudioLauncher = registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
        uri ?: return@registerForActivityResult
        // persist read permission so we can play later
        try {
            requireContext().contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
        } catch (e: Exception) {
            // ignore
        }

        // extract display name and duration, then add via ViewModel
        lifecycleScope.launch {
            val resolver = requireContext().contentResolver
            var displayName = uri.lastPathSegment ?: "Audio"
            resolver.query(uri, arrayOf(OpenableColumns.DISPLAY_NAME), null, null, null)?.use { c ->
                if (c.moveToFirst()) {
                    displayName = c.getString(0) ?: displayName
                }
            }

            var durationMs = 0L
            try {
                val mmr = MediaMetadataRetriever()
                mmr.setDataSource(requireContext(), uri)
                val dur = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
                durationMs = dur?.toLongOrNull() ?: 0L
                mmr.release()
            } catch (e: Exception) {
                // ignore
            }

            viewModel.addTrack(displayName, uri.toString(), durationMs) { id ->
                // optional: show a brief message in UI
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = LibraryViewModel(requireActivity().application)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_library, container, false)
        val btnImportDemo = v.findViewById<Button>(R.id.btnImportDemo)
        val fabImport = v.findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton?>(R.id.fabImport)
        val tvEmpty = v.findViewById<TextView>(R.id.tvEmptyMessage)

        // Optional RecyclerView if present in future layouts
        val rv = v.findViewById<RecyclerView?>(R.id.rvTracks)
        val adapter = TrackAdapter()
        rv?.layoutManager = LinearLayoutManager(requireContext())
        rv?.adapter = adapter

        // Observe tracks from ViewModel and update UI
        viewModel.tracks.observe(viewLifecycleOwner, Observer { list ->
            if (list.isNullOrEmpty()) {
                tvEmpty.text = "No tracks"
                tvEmpty.visibility = View.VISIBLE
                rv?.visibility = View.GONE
            } else {
                val sb = StringBuilder()
                list.forEach { sb.append("• ").append(it.displayName).append("\n") }
                tvEmpty.visibility = View.GONE
                rv?.visibility = View.VISIBLE
                adapter.submitList(list)
            }
        })

        // Demo import button: keep demo tone import
        btnImportDemo.setOnClickListener {
            btnImportDemo.isEnabled = false
            viewModel.importDemo { id, uri ->
                requireActivity().runOnUiThread {
                    tvEmpty.text = "Imported demo track id=$id\nuri=$uri"
                    btnImportDemo.isEnabled = true
                    // Play immediately for quick test
                    PlayerController.initialize(requireContext())
                    PlayerController.playTracks(listOf(Uri.parse(uri)))
                }
            }
        }

        // FAB: open system picker for audio
        fabImport?.setOnClickListener {
            // allow user to pick audio files
            pickAudioLauncher.launch(arrayOf("audio/*"))
        }

        viewModel.loadTracks()

        return v
    }
}
