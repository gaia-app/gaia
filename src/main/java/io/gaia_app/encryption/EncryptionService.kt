package io.gaia_app.encryption

/**
 * Service for encryption/decryption of data
 */
interface EncryptionService {

    fun encrypt(plaintext:String): String

    fun decrypt(cipherText: String): String

}
