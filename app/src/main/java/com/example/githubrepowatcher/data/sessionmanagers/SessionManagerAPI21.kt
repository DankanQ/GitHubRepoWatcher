package com.example.githubrepowatcher.data.sessionmanagers

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

/**
 * Implements the SessionRepository interface using EncryptedSharedPreferences to store
 * and retrieve the authToken.
 */
class SessionManagerAPI21(applicationContext: Context) {
    val authTokenKey = "auth_token_key"
    private val sharedPrefName = "key_value_storage"

    // Creates a MasterKey instance
    private val masterKey = MasterKey.Builder(applicationContext)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    // Creates an instance of EncryptedSharedPreferences with the given master key
    val sharedPreferences = EncryptedSharedPreferences.create(
        applicationContext,
        sharedPrefName,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
}