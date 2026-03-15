package com.example.kts_project.presentation.viewmodel.loginviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kts_project.domain.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.kts_project.domain.model.AuthError

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LoginUiState())
    val state: StateFlow<LoginUiState> = _state

    private val _events = MutableSharedFlow<LoginUiEvent>()
    val events: SharedFlow<LoginUiEvent> = _events.asSharedFlow()

    fun onUsernameChanged(newName: String) = _state.update { it.copy(userName = newName, errorType = null) }

    fun onPasswordChanged(newPassword: String) = _state.update { it.copy(password = newPassword, errorType = null) }

    fun onLogin() = viewModelScope.launch {
        _state.update { it.copy(isLoginButtonActive = true, errorType = null) }

        val result = loginRepository.login(
            userName = _state.value.userName,
            password = _state.value.password
        )

        result.fold(
            onSuccess = {
                _events.emit(LoginUiEvent.LoginSuccessEvent)
                _state.update { it.copy(isLoginButtonActive = true) }
            },
            onFailure = { exception ->

                val errorType = when (exception) {
                    is AuthError.EmptyEmail -> ErrorType.EMPTY_EMAIL
                    is AuthError.EmptyPassword -> ErrorType.EMPTY_PASSWORD
                    else -> ErrorType.UNKNOWN_ERROR
                }

                _state.update {
                    it.copy(
                        isLoginButtonActive = false,
                        errorType = if (errorType == ErrorType.EMPTY_EMAIL ||
                                errorType == ErrorType.EMPTY_PASSWORD)
                                                                errorType else null)
                }
            }
        )
    }
}
