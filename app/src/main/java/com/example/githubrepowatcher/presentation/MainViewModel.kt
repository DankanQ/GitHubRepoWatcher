package com.example.githubrepowatcher.presentation

import androidx.lifecycle.ViewModel
import com.example.githubrepowatcher.domain.models.KeyValueStorage
import com.example.githubrepowatcher.domain.usecases.SessionUseCase
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val sessionUseCase: SessionUseCase
) : ViewModel() {
    fun saveAuthToken(keyValueStorage: KeyValueStorage) =
        sessionUseCase.saveAuthToken(keyValueStorage)

    fun getAuthToken(): KeyValueStorage? = sessionUseCase.getAuthToken()

    fun clearAuthToken() = sessionUseCase.clearAuthToken()
}