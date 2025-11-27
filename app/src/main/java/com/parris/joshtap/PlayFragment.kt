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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import com.parris.joshtap.nfc.NfcPlayActivity
import com.parris.joshtap.nfc.NfcWriteActivity
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

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

        // Observe player state and errors in lifecycle-safe collectors
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    PlayerController.isPlaying.collect { isPlaying ->
                        btnPlayPause.text = if (isPlaying) "Pause" else "Play"
                    }
                }

                launch {
                    PlayerController.currentTitle.collect { title ->
                        tvNowPlaying.text = "Now Playing: ${title ?: "-"}"
                    }
                }

                launch {
                    PlayerController.currentIndex.collect { index ->
                        tvDebug.text = "Track ${index + 1}"
                    }
                }

                launch {
                    PlayerController.duration.collect { duration ->
                        tvDebug.text = "${tvDebug.text}\nDuration: ${formatTime(duration)}"
                    }
                }

                launch {
                    PlayerController.hasNext.collect { hasNext ->
                        btnNext.isEnabled = hasNext
                    }
                }

                launch {
                    PlayerController.hasPrevious.collect { hasPrev ->
                        btnPrev.isEnabled = hasPrev
                    }
                }

                // collect errors and show as Snackbar
                launch {
                    PlayerController.errorMessage.collect { err ->
                        if (!err.isNullOrBlank()) {
                            val parent = view.findViewById<View>(android.R.id.content) ?: view
                            Snackbar.make(parent, err, Snackbar.LENGTH_LONG).show()
                            PlayerController.clearError()
                        }
                    }
                }
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

    override fun onResume() {
        super.onResume()
        PlayerController.resumeUpdates()
    }

    override fun onPause() {
        super.onPause()
        PlayerController.pauseUpdates()
    }

    private fun formatTime(millis: Long): String {
        val seconds = (millis / 1000) % 60
        val minutes = (millis / 1000 / 60) % 60
        return String.format("%02d:%02d", minutes, seconds)
    }
}
