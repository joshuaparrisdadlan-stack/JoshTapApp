package com.parris.joshtap.util

import java.security.SecureRandom
import java.util.Base64

object TokenGenerator {
    private val rng = SecureRandom()

    fun generateToken(length: Int = 12): String {
        val bytes = ByteArray(length)
        rng.nextBytes(bytes)
        // URL-safe base64 without padding
        val token = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes)
        // trim to requested length (URL-safe)
        return if (token.length > length) token.substring(0, length) else token
    }
}
