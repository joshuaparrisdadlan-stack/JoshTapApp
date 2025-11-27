package com.parris.yotolite.nfc

import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.parris.yotolite.R
import kotlinx.coroutines.launch

/**
 * NfcWriteActivity handles NFC write mode.
 * User can select a card by name or token, then write it to an NFC tag.
 */
class NfcWriteActivity : AppCompatActivity() {
    private lateinit var etCardToken: EditText
    private lateinit var tvWriteStatus: TextView
    private lateinit var btnWriteCard: Button
    private lateinit var btnBackHome: Button
    private var pendingToken: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nfc_write)

        etCardToken = findViewById(R.id.etCardToken)
        tvWriteStatus = findViewById(R.id.tvWriteStatus)
        btnWriteCard = findViewById(R.id.btnWriteCard)

        // Back button
        btnBackHome = findViewById(R.id.btnBackHome)
        btnBackHome.setOnClickListener { finish() }

        btnWriteCard.setOnClickListener {
            val token = etCardToken.text.toString().trim()
            if (token.isEmpty()) {
                Toast.makeText(this, "Please enter a card token", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            pendingToken = token
            tvWriteStatus.text = "Tap an NFC tag to write token: $token"
            btnWriteCard.isEnabled = false
        }
    }

    override fun onResume() {
        super.onResume()
        NfcHandler.enableForegroundDispatch(
            this, this,
            arrayOf(IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED)),
            arrayOf(
                arrayOf("android.nfc.tech.Ndef"),
                arrayOf("android.nfc.tech.NdefFormatable")
            )
        )
    }

    override fun onPause() {
        super.onPause()
        NfcHandler.disableForegroundDispatch(this, this)
        btnWriteCard.isEnabled = true
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        if (intent.action == NfcAdapter.ACTION_TAG_DISCOVERED) {
            val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
            if (tag != null && pendingToken != null) {
                lifecycleScope.launch {
                    writeNfcTag(tag, pendingToken!!)
                }
            }
        }
    }

    private suspend fun writeNfcTag(tag: Tag, token: String) {
        tvWriteStatus.text = "Writing token to tag..."

        val success = NfcHandler.writeTokenToTag(tag, token)

        if (success) {
            tvWriteStatus.text = "Success! Card token written to tag:\n$token"
            Toast.makeText(this, "Card written successfully", Toast.LENGTH_SHORT).show()
            pendingToken = null
            etCardToken.text.clear()
        } else {
            tvWriteStatus.text = "Failed to write to tag. Try another tag or format it first."
            Toast.makeText(this, "Write failed", Toast.LENGTH_SHORT).show()
        }

        btnWriteCard.isEnabled = true
    }
}
