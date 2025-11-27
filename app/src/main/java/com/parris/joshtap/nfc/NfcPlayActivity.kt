package com.parris.joshtap.nfc

import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.parris.joshtap.MyApplication
import com.parris.joshtap.PlayerController
import com.parris.joshtap.R
import kotlinx.coroutines.launch

/**
 * NfcPlayActivity handles NFC read mode.
 * When an NFC tag is scanned, it extracts the token, retrieves the card + tracks,
 * and plays the audio.
 */
class NfcPlayActivity : AppCompatActivity() {
    private lateinit var tvStatus: TextView
    private lateinit var btnRetry: Button
    private lateinit var btnBackHome: Button
    private var lastProcessedToken: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nfc_play)

        tvStatus = findViewById(R.id.tvNfcStatus)
        btnRetry = findViewById(R.id.btnNfcRetry)

        // Back button
        btnBackHome = findViewById(R.id.btnBackHome)
        btnBackHome.setOnClickListener { finish() }

        btnRetry.setOnClickListener {
            tvStatus.text = "Waiting for NFC tag..."
            lastProcessedToken = null
        }

        PlayerController.initialize(this)
        handleIntent(intent)
    }

    override fun onResume() {
        super.onResume()
        NfcHandler.enableForegroundDispatch(
            this, this,
            arrayOf(IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED)),
            arrayOf(arrayOf("android.nfc.tech.Ndef"))
        )
    }

    override fun onPause() {
        super.onPause()
        NfcHandler.disableForegroundDispatch(this, this)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        if (intent.action == NfcAdapter.ACTION_TAG_DISCOVERED) {
            val tag: Tag? = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG, Tag::class.java)
            if (tag != null) {
                lifecycleScope.launch {
                    processNfcTag(tag)
                }
            }
        }
    }

    private suspend fun processNfcTag(tag: Tag) {
        tvStatus.text = "Reading NFC tag..."

        val token = NfcHandler.readTokenFromTag(tag)
        if (token == null) {
            tvStatus.text = "Error: Invalid tag format"
            Toast.makeText(this, "Could not read token from tag", Toast.LENGTH_SHORT).show()
            return
        }

        // Debounce: don't process same token twice
        if (token == lastProcessedToken) {
            tvStatus.text = "Tag already processed"
            return
        }
        lastProcessedToken = token

        tvStatus.text = "Retrieved token: $token\nFetching card..."

        // Query database for card + tracks
        val app = application as MyApplication
        val cardWithTracks = app.db.appDao().getCardWithTracksByToken(token)

        if (cardWithTracks == null) {
            tvStatus.text = "Error: Card not found for token $token"
            Toast.makeText(this, "Card not found", Toast.LENGTH_SHORT).show()
            return
        }

        if (cardWithTracks.tracks.isEmpty()) {
            tvStatus.text = "Card found but has no tracks assigned"
            Toast.makeText(this, "No tracks assigned to this card", Toast.LENGTH_SHORT).show()
            return
        }

        // Convert track URIs and play
        val trackUris = cardWithTracks.tracks.mapNotNull { track ->
            try {
                android.net.Uri.parse(track.localUri)
            } catch (ex: Exception) {
                null
            }
        }

        if (trackUris.isEmpty()) {
            tvStatus.text = "Error: No valid track URIs"
            Toast.makeText(this, "No valid track URIs found", Toast.LENGTH_SHORT).show()
            return
        }

        tvStatus.text = """
            Card: ${cardWithTracks.card.name}
            Token: $token
            Tracks: ${cardWithTracks.tracks.size}
            Now playing...
        """.trimIndent()

        PlayerController.playTracks(trackUris)
        Toast.makeText(this, "Playing ${cardWithTracks.card.name}", Toast.LENGTH_SHORT).show()
    }
}
