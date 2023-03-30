package com.example.githubrepowatcher.domain.repository

import com.example.githubrepowatcher.domain.models.KeyValueStorage

interface SessionRepository {
    fun saveAuthToken(keyValueStorage: KeyValueStorage)

    fun getAuthToken(): KeyValueStorage?

    fun clearAuthToken()
}