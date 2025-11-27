package com.parris.yotolite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class LibraryFragment : Fragment() {
    private lateinit var viewModel: LibraryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = LibraryViewModel(requireActivity().application)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_library, container, false)
        val btnImport = v.findViewById<Button>(R.id.btnImport)
        val tvLibrary = v.findViewById<TextView>(R.id.tvLibrary)

        btnImport.setOnClickListener {
            btnImport.isEnabled = false
            viewModel.importDemo { id, uri ->
                requireActivity().runOnUiThread {
                    tvLibrary.text = "Imported demo track id=$id\nuri=$uri"
                    btnImport.isEnabled = true
                    // Play immediately for quick test
                    PlayerController.initialize(requireContext())
                    PlayerController.playTracks(listOf(android.net.Uri.parse(uri)))
                }
            }
        }

        return v
    }
}
