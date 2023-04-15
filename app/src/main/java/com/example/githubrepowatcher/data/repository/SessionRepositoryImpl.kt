package com.example.githubrepowatcher.data.repository

import android.app.Application
import android.os.Build
import com.example.githubrepowatcher.domain.models.KeyValueStorage
import com.example.githubrepowatcher.data.SessionManager
import com.example.githubrepowatcher.domain.repository.SessionRepository
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import javax.inject.Inject

class SessionRepositoryImpl @Inject constructor(
    private val application: Application,
    private val keyValueStorage: KeyValueStorage
) : SessionRepository {
    private val sessionManager = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        SessionManager.SessionManagerAPI23()
    } else {
        SessionManager.SessionManagerAPI21(application)
    }

    override fun saveAuthToken(keyValueStorage: KeyValueStorage) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            with(sessionManager as SessionManager.SessionManagerAPI23) {
                val bytes = keyValueStorage.authToken!!.encodeToByteArray()
                val file = File(
                    application.filesDir,
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
                    application.filesDir,
                    getKeyStoreFileName()
                )
                return if (file.exists()) {
                    FileInputStream(file).use { inputStream ->
                        val authToken = decrypt(
                            inputStream = inputStream
                        ).decodeToString()
                        keyValueStorage.authToken = authToken
                        return keyValueStorage
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
                keyValueStorage.authToken = authToken
                return keyValueStorage
            }
        }
    }

    override fun clearAuthToken() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            with(sessionManager as SessionManager.SessionManagerAPI23) {
                val file = File(
                    application.filesDir,
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