package com.example.githubrepowatcher.presentation.viewmodels

import android.app.Application
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import com.example.githubrepowatcher.data.repository.SessionRepositoryAPI21Impl
import com.example.githubrepowatcher.data.repository.SessionRepositoryAPI23Impl
import com.example.githubrepowatcher.domain.models.KeyValueStorage
import com.example.githubrepowatcher.domain.usecases.SessionUseCase

class SessionViewModel(context: Application) : AndroidViewModel(context) {
    private val sessionRepositoryImpl = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        SessionRepositoryAPI23Impl(context)
    } else {
        SessionRepositoryAPI21Impl(context)
    }

    private val sessionUseCase = SessionUseCase(sessionRepositoryImpl)

    fun saveAuthToken(keyValueStorage: KeyValueStorage) {
        sessionUseCase.saveAuthToken(
            keyValueStorage
        )
    }

    fun getAuthToken(): KeyValueStorage? = sessionUseCase.getAuthToken()

    fun clearAuthToken() = sessionUseCase.clearAuthToken()
}