package com.example.githubrepowatcher.data.sessionmanager

import android.content.Context
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.annotation.RequiresApi
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import java.io.InputStream
import java.io.OutputStream
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

sealed interface SessionManager {
    // SessionManager with using EncryptedSharedPreferences for API 21
    class SessionManagerAPI21(context: Context) : SessionManager {
        private val authTokenKey = "auth_token_key"
        private val sharedPrefName = "key_value_storage"

        // MasterKey for encrypting and decrypting
        private val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        private val sharedPreferences = EncryptedSharedPreferences.create(
            context,
            sharedPrefName,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        ) as EncryptedSharedPreferences

        fun getAuthTokenKey() = authTokenKey
        fun getSharedPreferences() = sharedPreferences
    }

    // SessionManager with using KeyStore for API 23+
    @RequiresApi(Build.VERSION_CODES.M)
    class SessionManagerAPI23 : SessionManager {
        private val keyStoreFileName = "keystore.jks"

        private val keyStore = KeyStore.getInstance("AndroidKeyStore").apply {
            load(null)
        }

        private val encryptCipher = Cipher.getInstance(TRANSFORMATION).apply {
            init(Cipher.ENCRYPT_MODE, getKey())
        }

        private fun decryptCipher(iv: ByteArray): Cipher {
            return Cipher.getInstance(TRANSFORMATION).apply {
                init(Cipher.DECRYPT_MODE, getKey(), IvParameterSpec(iv))
            }
        }

        private fun getKey(): SecretKey {
            val existingKey = keyStore.getEntry(
                KEYSTORE_ALIAS,
                null
            ) as? KeyStore.SecretKeyEntry
            return existingKey?.secretKey ?: createKey()
        }

        private fun createKey(): SecretKey {
            return KeyGenerator.getInstance(ALGORITHM).apply {
                init(
                    KeyGenParameterSpec.Builder(
                        KEYSTORE_ALIAS,
                        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                    )
                        .setBlockModes(BLOCK_CODE)
                        .setEncryptionPaddings(PADDING)
                        .setUserAuthenticationRequired(false)
                        .setRandomizedEncryptionRequired(true)
                        .build()
                )
            }.generateKey()
        }

        fun encrypt(bytes: ByteArray, outputStream: OutputStream): ByteArray {
            val encryptedBytes = encryptCipher.doFinal(bytes)
            outputStream.use {
                it.write(encryptCipher.iv.size)
                it.write(encryptCipher.iv)
                it.write(encryptedBytes.size)
                it.write(encryptedBytes)
            }
            return encryptedBytes
        }

        fun decrypt(inputStream: InputStream): ByteArray {
            return inputStream.use {
                val ivSize = it.read()
                val iv = ByteArray(ivSize)
                it.read(iv)

                val encryptedBytesSize = it.read()
                val encryptedBytes = ByteArray(encryptedBytesSize)
                it.read(encryptedBytes)

                decryptCipher(iv).doFinal(encryptedBytes)
            }
        }

        fun getKeyStoreFileName() = keyStoreFileName

        companion object {
            private const val ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
            private const val BLOCK_CODE = KeyProperties.BLOCK_MODE_CBC
            private const val PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7
            private const val TRANSFORMATION = "$ALGORITHM/$BLOCK_CODE/$PADDING"

            private const val KEYSTORE_ALIAS = "authTokenKey"
        }
    }
}