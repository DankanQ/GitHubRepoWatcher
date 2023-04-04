package com.example.githubrepowatcher.domain.usecases

import com.example.githubrepowatcher.domain.models.KeyValueStorage
import com.example.githubrepowatcher.domain.repository.SessionRepository
import javax.inject.Inject

class SessionUseCase @Inject constructor(
    private val sessionRepository: SessionRepository
) {
    fun saveToken(keyValueStorage: KeyValueStorage) =
        sessionRepository.saveAuthToken(keyValueStorage)

    fun getToken(): KeyValueStorage? = sessionRepository.getAuthToken()

    fun clearAuthToken() = sessionRepository.clearAuthToken()
}