package com.example.githubrepowatcher.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.githubrepowatcher.data.repository.SessionRepositoryImpl
import com.example.githubrepowatcher.domain.models.KeyValueStorage
import com.example.githubrepowatcher.domain.usecases.SessionUseCase
import javax.inject.Inject

class MainViewModel @Inject constructor(
    context: Application
) : AndroidViewModel(context) {
    private val sessionRepositoryImpl = SessionRepositoryImpl(context)

    private val sessionUseCase = SessionUseCase(sessionRepositoryImpl)

    fun saveAuthToken(keyValueStorage: KeyValueStorage) =
        sessionUseCase.saveAuthToken(keyValueStorage)

    fun getAuthToken(): KeyValueStorage? = sessionUseCase.getAuthToken()

    fun clearAuthToken() = sessionUseCase.clearAuthToken()
}