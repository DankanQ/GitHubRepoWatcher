package com.example.githubrepowatcher.presentation.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubrepowatcher.domain.models.UserInfo
import com.example.githubrepowatcher.domain.usecases.SignInUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
) : ViewModel() {
    private val token = MutableLiveData<String>()

    private val _state: MutableLiveData<State> = MutableLiveData()
    val state: LiveData<State> = _state

    private val _actions = MutableSharedFlow<Action>()
    val actions: Flow<Action> = _actions.asSharedFlow()

    private val _userInfo = MutableLiveData<UserInfo>()
    val userInfo: LiveData<UserInfo> = _userInfo

    fun setAuthToken(authToken: String) {
        token.value = authToken
    }

    fun onSignButtonPressed() {
        viewModelScope.launch {
            _state.value = State.Idle
            try {
                _state.value = State.Loading
                val authToken = token.value.toString()
                _userInfo.postValue(signInUseCase.invoke("Bearer $authToken"))
                _state.value = State.Idle
                _actions.emit(Action.RouteToMain)
            } catch (e: Exception) {
                _state.value = State.InvalidInput(e.message ?: "Invalid input")
                if (e is HttpException && e.code() == 401) {
                    _state.value = State.InvalidInput(e.message())
                } else {
                    _actions.emit(Action.ShowError(e.message ?: "Network error"))
                }
            }
        }
    }

    fun shouldHideError() {
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