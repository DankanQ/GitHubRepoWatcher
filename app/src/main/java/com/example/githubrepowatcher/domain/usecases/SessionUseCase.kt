package com.example.githubrepowatcher.domain.usecases

import com.example.githubrepowatcher.domain.models.KeyValueStorage
import com.example.githubrepowatcher.domain.repository.SessionRepository

class SessionUseCase(
    private val sessionRepository: SessionRepository
) {
    fun saveAuthToken(keyValueStorage: KeyValueStorage) = sessionRepository.saveAuthToken(
        keyValueStorage
    )

    fun getAuthToken(): KeyValueStorage? = sessionRepository.getAuthToken()

    fun clearAuthToken() = sessionRepository.clearAuthToken()
}