package com.example.githubrepowatcher.data.repository

import android.content.Context
import android.os.Build
import com.example.githubrepowatcher.data.sessionmanager.SessionManager
import com.example.githubrepowatcher.domain.models.KeyValueStorage
import com.example.githubrepowatcher.domain.repository.SessionRepository
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class SessionRepositoryImpl(
    private val context: Context
) : SessionRepository {
    private val sessionManager = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        SessionManager.SessionManagerAPI23()
    } else {
        SessionManager.SessionManagerAPI21(context)
    }

    override fun saveAuthToken(keyValueStorage: KeyValueStorage) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            with(sessionManager as SessionManager.SessionManagerAPI23) {
                val bytes = keyValueStorage.authToken!!.encodeToByteArray()
                val file = File(
                    context.filesDir,
                    getKeyStoreFileName()
                )
                if (!file.exists()) {
                    file.createNewFile()
                }
                FileOutputStream(file).use { outputStream ->
                    encrypt(bytes = bytes, outputStream = outputStream)
                        .decodeToString()
                }
            }
        } else {
            with(sessionManager as SessionManager.SessionManagerAPI21) {
                val sharedPreferences = getSharedPreferences()
                sharedPreferences.edit()
                    .putString(getAuthTokenKey(), keyValueStorage.authToken)
                    .apply()
            }
        }
    }

    override fun getAuthToken(): KeyValueStorage? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            with(sessionManager as SessionManager.SessionManagerAPI23) {
                val file = File(
                    context.filesDir,
                    getKeyStoreFileName()
                )
                return if (file.exists()) {
                    FileInputStream(file).use { inputStream ->
                        val authToken = decrypt(
                            inputStream = inputStream
                        ).decodeToString()
                        KeyValueStorage(authToken)
                    }
                } else {
                    null
                }
            }
        } else {
            with(sessionManager as SessionManager.SessionManagerAPI21) {
                val sharedPreferences = getSharedPreferences()
                val authToken = sharedPreferences.getString(
                    getAuthTokenKey(),
                    null
                ) ?: return null
                return KeyValueStorage(authToken)
            }
        }
    }

    override fun clearAuthToken() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            with(sessionManager as SessionManager.SessionManagerAPI23) {
                val file = File(
                    context.filesDir,
                    getKeyStoreFileName()
                )
                if (file.exists()) file.delete()
            }
        } else {
            with(sessionManager as SessionManager.SessionManagerAPI21) {
                val sharedPreferences = getSharedPreferences()
                sharedPreferences.edit()
                    .remove(getAuthTokenKey())
                    .apply()
            }
        }
    }
}