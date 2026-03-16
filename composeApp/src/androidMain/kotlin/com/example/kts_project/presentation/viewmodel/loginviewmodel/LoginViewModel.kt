package com.example.kts_project.presentation.viewmodel.loginviewmodel

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kts_project.data.auth.AuthService
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
import io.github.aakira.napier.Napier
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationResponse
import okhttp3.Response

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authService: AuthService
) : ViewModel() {

    private val _state = MutableStateFlow(LoginUiState())
    val state: StateFlow<LoginUiState> = _state


    private val _events = MutableSharedFlow<LoginUiEvent>()
    val events: SharedFlow<LoginUiEvent> = _events.asSharedFlow()

    fun getAuthIntent() : Intent {
        val request = authService.getAuthRequest()
        return authService.getAuthIntent(request)
    }

    fun handleAuthResponse(
        intent: Intent
    ) {
        val response = AuthorizationResponse.fromIntent(intent)
        val exception = AuthorizationException.fromIntent(intent)

        if (response == null) {
            Napier.e("Auth error: ${exception?.message}", tag = "Auth")
            _state.update { it.copy(errorType = ErrorType.UNKNOWN_ERROR) }
            return
        }

        _state.update { it.copy(isLoginButtonActive = false) }

        authService.performTokenRequest(
            response = response,
            onSuccess = { _, _ ->
                viewModelScope.launch {
                    _events.emit(LoginUiEvent.LoginSuccessEvent)
                }
            },
            onError = {
                _state.update { it.copy(isLoginButtonActive = true, errorType = ErrorType.UNKNOWN_ERROR) }
            }
        )
    }
}
