package com.parris.yotolite.nfc

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.nfc.tech.NdefFormatable
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

/**
 * NfcHandler manages NFC read and write operations for Type 2 tags.
 * - Read mode: Extracts token from NDEF URI and retrieves card + tracks
 * - Write mode: Encodes token as NDEF URI and writes to tag
 */
object NfcHandler {
    private const val TAG = "NfcHandler"
    private const val SCHEME = "https://yotolite.app/play/"
    private const val MIME_TYPE = "text/plain"

    /**
     * Initializes NFC foreground dispatch for MainActivity.
     * Call from onCreate() and onResume().
     */
    fun enableForegroundDispatch(
        context: Context,
        activity: android.app.Activity,
        intentFilter: Array<android.content.IntentFilter>,
        techList: Array<Array<String>>
    ) {
        val nfcAdapter = NfcAdapter.getDefaultAdapter(context)
        if (nfcAdapter == null) {
            Log.w(TAG, "NFC not supported on this device")
            return
        }

        val pendingIntent = PendingIntent.getActivity(
            context, 0,
            Intent(context, activity::class.java).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        try {
            nfcAdapter.enableForegroundDispatch(activity, pendingIntent, intentFilter, techList)
            Log.d(TAG, "NFC foreground dispatch enabled")
        } catch (ex: Exception) {
            Log.e(TAG, "Error enabling foreground dispatch", ex)
        }
    }

    /**
     * Disables NFC foreground dispatch for MainActivity.
     * Call from onPause().
     */
    fun disableForegroundDispatch(context: Context, activity: android.app.Activity) {
        val nfcAdapter = NfcAdapter.getDefaultAdapter(context)
        nfcAdapter?.disableForegroundDispatch(activity)
        Log.d(TAG, "NFC foreground dispatch disabled")
    }

    /**
     * Parses token from an NFC tag's NDEF message.
     * Extracts from URI record: "https://yotolite.app/play/{token}"
     */
    suspend fun readTokenFromTag(tag: Tag): String? = withContext(Dispatchers.IO) {
        try {
            val ndef = Ndef.get(tag) ?: return@withContext null
            ndef.connect()
            val message = ndef.ndefMessage ?: return@withContext null
            ndef.close()

            val token = parseTokenFromNdef(message)
            Log.d(TAG, "Token read from tag: $token")
            token
        } catch (ex: IOException) {
            Log.e(TAG, "Error reading NDEF from tag", ex)
            null
        }
    }

    /**
     * Writes token to an NFC tag using NDEF format.
     * Encodes as URI record: "https://yotolite.app/play/{token}"
     */
    suspend fun writeTokenToTag(tag: Tag, token: String): Boolean = withContext(Dispatchers.IO) {
        try {
            val ndefRecord = createUriRecord(token)
            val ndefMessage = NdefMessage(arrayOf(ndefRecord))

            val ndef = Ndef.get(tag)
            if (ndef != null) {
                ndef.connect()
                if (ndef.isWritable) {
                    ndef.writeNdefMessage(ndefMessage)
                    ndef.close()
                    Log.d(TAG, "Token written to tag: $token")
                    return@withContext true
                }
                ndef.close()
            } else {
                // Try formatting as NDEF if not already formatted
                val ndefFormatable = NdefFormatable.get(tag)
                if (ndefFormatable != null) {
                    ndefFormatable.connect()
                    ndefFormatable.format(ndefMessage)
                    ndefFormatable.close()
                    Log.d(TAG, "Tag formatted and token written: $token")
                    return@withContext true
                }
            }

            Log.w(TAG, "Tag is not writable")
            return@withContext false
        } catch (ex: IOException) {
            Log.e(TAG, "Error writing NDEF to tag", ex)
            return@withContext false
        }
    }

    /**
     * Parses token from NDEF message.
     * Looks for URI record with scheme "https://yotolite.app/play/{token}"
     */
    private fun parseTokenFromNdef(message: NdefMessage): String? {
        for (record in message.records) {
            if (record.tnf == NdefRecord.TNF_WELL_KNOWN && record.type contentEquals NdefRecord.RTD_URI) {
                val uri = String(record.payload, 1, record.payload.size - 1)
                Log.d(TAG, "Found URI record: $uri")

                if (uri.startsWith(SCHEME)) {
                    val token = uri.substring(SCHEME.length)
                    if (token.isNotEmpty()) {
                        return token
                    }
                }
            }
        }
        Log.w(TAG, "No valid token found in NDEF message")
        return null
    }

    /**
     * Creates an NDEF URI record with the token.
     * URI format: "https://yotolite.app/play/{token}"
     */
    private fun createUriRecord(token: String): NdefRecord {
        val uri = SCHEME + token
        val uriBytes = uri.toByteArray(Charsets.UTF_8)

        // NDEF URI record format: first byte is prefix (0x00 for unabbreviated https://)
        val payload = ByteArray(uriBytes.size + 1)
        payload[0] = 0x04.toByte() // https:// prefix
        System.arraycopy(uriBytes, 0, payload, 1, uriBytes.size)

        return NdefRecord(
            NdefRecord.TNF_WELL_KNOWN,
            NdefRecord.RTD_URI,
            ByteArray(0),
            payload
        )
    }

    /**
     * Checks if NFC is available on device and enabled.
     */
    fun isNfcAvailable(context: Context): Boolean {
        val nfcAdapter = NfcAdapter.getDefaultAdapter(context)
        return nfcAdapter != null && nfcAdapter.isEnabled
    }

    /**
     * Checks if device supports NFC hardware.
     */
    fun isNfcSupported(context: Context): Boolean {
        return NfcAdapter.getDefaultAdapter(context) != null
    }
}
