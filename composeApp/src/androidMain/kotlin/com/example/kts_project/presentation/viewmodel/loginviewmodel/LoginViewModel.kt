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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LoginUiState())
    val state: StateFlow<LoginUiState> = _state

    private val _events = MutableSharedFlow<LoginUiEvent>()
    val events: SharedFlow<LoginUiEvent> = _events.asSharedFlow()

    fun onUsernameChanged(newName: String) = _state.update { it.copy(userName = newName, error = null) }

    fun onPasswordChanged(newPassword: String) = _state.update { it.copy(password = newPassword, error = null) }

    fun onLogin() = viewModelScope.launch {
        _state.update { it.copy(isLoginButtonActive = true, error = null) }

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
                _state.update {
                    it.copy(
                        isLoginButtonActive = false,
                        error = exception.message ?: "Ошибка входа"
                    )
                }
            }
        )
    }
}
