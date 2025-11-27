package com.parris.yotolite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.parris.yotolite.nfc.NfcPlayActivity
import com.parris.yotolite.nfc.NfcWriteActivity

class PlayFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_play, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnNfcPlay = view.findViewById<Button>(R.id.btnNfcPlay)
        val btnNfcWrite = view.findViewById<Button>(R.id.btnNfcWrite)

        btnNfcPlay.setOnClickListener {
            startActivity(Intent(requireContext(), NfcPlayActivity::class.java))
        }

        btnNfcWrite.setOnClickListener {
            startActivity(Intent(requireContext(), NfcWriteActivity::class.java))
        }
    }
}
