package com.fpstudio.stretchreminder.data.util

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

object TokenGenerator {
    private const val SECRET_KEY = "clave_secreta_123"
    private const val HMAC_ALGORITHM = "HmacSHA256"
    
    /**
     * Generates HMAC-SHA256 token for API authentication
     * @param message Optional message to hash (default: empty string)
     * @return Hexadecimal token string
     */
    fun generateHmacToken(message: String = ""): String {
        return try {
            val sha256HMAC = Mac.getInstance(HMAC_ALGORITHM)
            val secretKey = SecretKeySpec(SECRET_KEY.toByteArray(Charsets.UTF_8), HMAC_ALGORITHM)
            sha256HMAC.init(secretKey)
            
            val hash = sha256HMAC.doFinal(message.toByteArray(Charsets.UTF_8))
            hash.joinToString("") { "%02x".format(it) }
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }
}
