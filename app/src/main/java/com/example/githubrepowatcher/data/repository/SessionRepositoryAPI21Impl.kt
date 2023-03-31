package com.example.githubrepowatcher.data.repository

import android.content.Context
import com.example.githubrepowatcher.data.sessionmanagers.SessionManagerAPI21
import com.example.githubrepowatcher.domain.models.KeyValueStorage
import com.example.githubrepowatcher.domain.repository.SessionRepository

class SessionRepositoryAPI21Impl(
    private val applicationContext: Context,
) : SessionRepository {
    private val sessionManager = SessionManagerAPI21(applicationContext)

    override fun saveAuthToken(keyValueStorage: KeyValueStorage) {
        sessionManager.sharedPreferences.edit()
            .putString(sessionManager.authTokenKey, keyValueStorage.authToken)
            .apply()
    }

    override fun getAuthToken(): KeyValueStorage? {
        val authToken = sessionManager.sharedPreferences.getString(
            sessionManager.authTokenKey,
            null
        ) ?: return null
        return KeyValueStorage(authToken)
    }

    override fun clearAuthToken() {
        sessionManager.sharedPreferences.edit()
            .remove(sessionManager.authTokenKey)
            .apply()
    }
}