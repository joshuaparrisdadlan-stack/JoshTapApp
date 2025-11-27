package com.parris.joshtap

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.parris.joshtap.nfc.NfcPlayActivity
import com.parris.joshtap.nfc.NfcWriteActivity
import kotlinx.coroutines.launch

class PlayFragment : Fragment() {
    private var isUserSeeking = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_play, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvNowPlaying = view.findViewById<TextView>(R.id.tvNowPlaying)
        val tvDebug = view.findViewById<TextView>(R.id.tvDebug)
        val btnPlayPause = view.findViewById<Button>(R.id.btnPlayPause)
        val btnPrev = view.findViewById<Button>(R.id.btnPrev)
        val btnNext = view.findViewById<Button>(R.id.btnNext)
        val btnNfcPlay = view.findViewById<Button>(R.id.btnNfcPlay)
        val btnNfcWrite = view.findViewById<Button>(R.id.btnNfcWrite)

        // Observe player state
        viewLifecycleOwner.lifecycleScope.launch {
            PlayerController.isPlaying.collect { isPlaying ->
                btnPlayPause.text = if (isPlaying) "Pause" else "Play"
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            PlayerController.currentTitle.collect { title ->
                tvNowPlaying.text = "Now Playing: ${title ?: "-"}"
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            PlayerController.currentIndex.collect { index ->
                tvDebug.text = "Track ${index + 1}"
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            PlayerController.duration.collect { duration ->
                tvDebug.text = "${tvDebug.text}\nDuration: ${formatTime(duration)}"
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            PlayerController.hasNext.collect { hasNext ->
                btnNext.isEnabled = hasNext
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            PlayerController.hasPrevious.collect { hasPrev ->
                btnPrev.isEnabled = hasPrev
            }
        }

        // Button listeners
        btnPlayPause.setOnClickListener {
            if (PlayerController.isPlaying.value) {
                PlayerController.pause()
            } else {
                PlayerController.play()
            }
        }

        btnNext.setOnClickListener {
            PlayerController.next()
        }

        btnPrev.setOnClickListener {
            PlayerController.prev()
        }

        btnNfcPlay.setOnClickListener {
            startActivity(Intent(requireContext(), NfcPlayActivity::class.java))
        }

        btnNfcWrite.setOnClickListener {
            startActivity(Intent(requireContext(), NfcWriteActivity::class.java))
        }

        PlayerController.initialize(requireContext())
    }

    private fun formatTime(millis: Long): String {
        val seconds = (millis / 1000) % 60
        val minutes = (millis / 1000 / 60) % 60
        return String.format("%02d:%02d", minutes, seconds)
    }
}
