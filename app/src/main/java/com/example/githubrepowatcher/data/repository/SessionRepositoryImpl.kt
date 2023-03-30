package com.example.githubrepowatcher.data.repository

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.githubrepowatcher.domain.models.KeyValueStorage
import com.example.githubrepowatcher.domain.repository.SessionRepository

/**
 * Implements the SessionRepository interface using EncryptedSharedPreferences to store
 * and retrieve the authToken.
 */
class SessionRepositoryImpl(
    applicationContext: Context,
) : SessionRepository {
    private val authTokenKey = "auth_token_key"
    private val sharedPrefName = "key_value_storage"

    // Creates a MasterKey instance
    // that will be used for encrypting/decrypting the SharedPreferences data
    private val masterKey = MasterKey.Builder(applicationContext)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    // Creates an instance of EncryptedSharedPreferences
    // with the given master key and other settings
    private val sharedPreferences = EncryptedSharedPreferences.create(
        applicationContext,
        sharedPrefName,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    override fun saveAuthToken(keyValueStorage: KeyValueStorage) {
        sharedPreferences.edit()
            .putString(authTokenKey, keyValueStorage.authToken)
            .apply()
    }

    override fun getAuthToken(): KeyValueStorage? {
        val authToken = sharedPreferences.getString(
            authTokenKey,
            null
        ) ?: return null
        return KeyValueStorage(authToken)
    }

    override fun clearAuthToken() {
        sharedPreferences.edit()
            .remove(authTokenKey)
            .apply()
    }
}