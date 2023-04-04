package com.example.githubrepowatcher.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubrepowatcher.domain.models.KeyValueStorage
import com.example.githubrepowatcher.domain.usecases.SessionUseCase
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val sessionUseCase: SessionUseCase
) : ViewModel() {
    private val _isAuth = MutableLiveData<Boolean>()
    val isAuth: LiveData<Boolean> = _isAuth

    private val _token = MutableLiveData<String>()
    val token: LiveData<String> = _token

    private fun getAuthToken(): KeyValueStorage? = sessionUseCase.getToken()

    fun checkAuth() {
        val keyValueStorage = getAuthToken()
        val authToken = keyValueStorage?.authToken ?: UNDEFINED_AUTH_TOKEN
        if (authToken.isNotEmpty()) {
            _token.value = authToken
            _isAuth.value = true
        } else {
            _isAuth.value = false
        }
    }

    fun saveAuthToken(keyValueStorage: KeyValueStorage) =
        sessionUseCase.saveToken(keyValueStorage)

    fun clearAuthToken() = sessionUseCase.clearAuthToken()

    companion object {
        private const val UNDEFINED_AUTH_TOKEN = ""
    }
}