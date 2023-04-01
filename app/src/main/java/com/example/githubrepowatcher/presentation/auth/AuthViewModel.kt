package com.example.githubrepowatcher.presentation.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.githubrepowatcher.data.repository.AppRepositoryImpl
import com.example.githubrepowatcher.domain.models.UserInfo
import com.example.githubrepowatcher.domain.usecases.SignInUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class AuthViewModel(context: Application) : AndroidViewModel(context) {
    private val repository = AppRepositoryImpl()

    private val signInUseCase = SignInUseCase(repository)

    private val _token = MutableLiveData<String>()
    val token: LiveData<String> = _token

    private val _state: MutableLiveData<State> = MutableLiveData(State.Loading)
    val state: LiveData<State> = _state

    private val _actions = MutableSharedFlow<Action>()
    val actions: Flow<Action> = _actions.asSharedFlow()

    private val _userInfo = MutableLiveData<UserInfo>()
    val userInfo: LiveData<UserInfo> = _userInfo

    fun onSignButtonPressed(token: String) {
        viewModelScope.launch {
            _state.value = State.Loading
            try {
                _userInfo.postValue(signInUseCase.invoke("Bearer $token"))
                _token.value = token
                _state.value = State.Idle
                _actions.emit(Action.RouteToMain)
            } catch (e: Exception) {
                _state.value = State.InvalidInput(e.message ?: "Invalid input")
                if (e is HttpException && e.code() == 401) {
                    _state.value = State.InvalidInput(e.message())
                } else {
                    _actions.emit(Action.ShowError(e.message ?: "Unknown error"))
                }
            }
        }
    }

    fun hideError() {
        _state.value = State.Idle
    }

    sealed interface State {
        object Idle : State
        object Loading : State
        data class InvalidInput(val reason: String) : State
    }

    sealed interface Action {
        data class ShowError(val message: String) : Action
        object RouteToMain : Action
    }
}