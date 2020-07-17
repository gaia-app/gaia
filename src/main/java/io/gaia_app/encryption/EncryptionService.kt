package io.gaia_app.encryption

/**
 * Service for encryption/decryption of data
 */
interface EncryptionService {

    fun encrypt(plaintext:String): String

    fun encryptBatch(plaintext: List<String>): List<String>

    fun decrypt(cipherText: String): String

    fun decryptBatch(cipherText: List<String>): List<String>

}
