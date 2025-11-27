package com.parris.yotolite.nfc

import android.nfc.NdefRecord
import org.junit.Test
import org.junit.Assert.*
import java.util.Base64
import java.security.SecureRandom

class TokenParserTest {
    companion object {
        private const val SCHEME = "https://yotolite.app/play/"
    }

    @Test
    fun testValidTokenGeneration() {
        val token = generateToken()
        assertNotNull(token)
        assertTrue(token.isNotEmpty())
        assertTrue(token.length <= 20)
    }

    @Test
    fun testTokenParseFromUri() {
        val token = "testtoken12345"
        val uri = SCHEME + token

        val extracted = parseTokenFromUri(uri)
        assertEquals(token, extracted)
    }

    @Test
    fun testTokenParseFromUri_Invalid() {
        val uri = "https://example.com/invalid"

        val extracted = parseTokenFromUri(uri)
        assertNull(extracted)
    }

    @Test
    fun testTokenParseFromUri_Empty() {
        val uri = SCHEME

        val extracted = parseTokenFromUri(uri)
        assertNull(extracted)
    }

    @Test
    fun testUrlSafeBase64Encoding() {
        val bytes = ByteArray(16)
        SecureRandom().nextBytes(bytes)

        val encoded = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes)
        assertNotNull(encoded)
        assertFalse(encoded.contains("+"))
        assertFalse(encoded.contains("/"))
        assertFalse(encoded.endsWith("="))
    }

    @Test
    fun testNdefRecordCreation() {
        val token = "mynfctoken"
        val record = createNdefUriRecord(token)

        assertNotNull(record)
        assertEquals(NdefRecord.TNF_WELL_KNOWN, record.tnf.toInt())
        assertTrue(record.type.contentEquals(NdefRecord.RTD_URI))
    }

    @Test
    fun testNdefRecordPayload() {
        val token = "mytesttoken123"
        val record = createNdefUriRecord(token)

        // First byte should be prefix (0x04 for https://)
        assertEquals(0x04.toByte(), record.payload[0])

        // Rest is the URI
        val uri = String(record.payload, 1, record.payload.size - 1)
        assertTrue(uri.contains(token))
    }

    @Test
    fun testTokenWithSpecialCharacters() {
        val token = "abc_123-xyz"
        val uri = SCHEME + token

        val extracted = parseTokenFromUri(uri)
        assertEquals(token, extracted)
    }

    // Helper functions
    private fun generateToken(): String {
        val rng = SecureRandom()
        val bytes = ByteArray(12)
        rng.nextBytes(bytes)
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes).substring(0, 12)
    }

    private fun parseTokenFromUri(uri: String): String? {
        if (uri.startsWith(SCHEME)) {
            val token = uri.substring(SCHEME.length)
            return if (token.isNotEmpty()) token else null
        }
        return null
    }

    private fun createNdefUriRecord(token: String): NdefRecord {
        val uri = SCHEME + token
        val uriBytes = uri.toByteArray(Charsets.UTF_8)
        val payload = ByteArray(uriBytes.size + 1)
        payload[0] = 0x04.toByte()
        System.arraycopy(uriBytes, 0, payload, 1, uriBytes.size)

        return NdefRecord(
            NdefRecord.TNF_WELL_KNOWN,
            NdefRecord.RTD_URI,
            ByteArray(0),
            payload
        )
    }
}
