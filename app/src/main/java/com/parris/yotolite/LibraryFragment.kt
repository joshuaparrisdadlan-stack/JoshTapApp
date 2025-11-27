package com.parris.yotolite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.parris.yotolite.ui.TrackAdapter

class LibraryFragment : Fragment() {
    private lateinit var viewModel: LibraryViewModel

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

        // Demo import button
        btnImportDemo.setOnClickListener {
            btnImportDemo.isEnabled = false
            viewModel.importDemo { id, uri ->
                requireActivity().runOnUiThread {
                    tvEmpty.text = "Imported demo track id=$id\nuri=$uri"
                    btnImportDemo.isEnabled = true
                    // Play immediately for quick test
                    PlayerController.initialize(requireContext())
                    PlayerController.playTracks(listOf(android.net.Uri.parse(uri)))
                }
            }
        }

        // FAB performs same demo import for now
        fabImport?.setOnClickListener {
            btnImportDemo.performClick()
        }

        viewModel.loadTracks()

        return v
    }
}
