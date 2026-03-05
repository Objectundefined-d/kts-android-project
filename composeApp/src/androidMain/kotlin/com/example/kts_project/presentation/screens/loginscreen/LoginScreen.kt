package com.example.kts_project.presentation.screens.loginscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.kts_project.presentation.viewmodel.loginviewmodel.LoginUiEvent
import com.example.kts_project.presentation.viewmodel.loginviewmodel.LoginViewModel
import androidx.compose.ui.res.stringResource
import com.example.kts_project.R
import com.example.kts_project.presentation.viewmodel.loginviewmodel.ErrorType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreenContent(
    userName: String,
    password: String,
    isLoginButtonActive: Boolean,
    errorType: ErrorType? = null,
    onBack: () -> Unit,
    onUsernameChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onLogin: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.navigation_back),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.primary
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .systemBarsPadding()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 30.dp),
                text = stringResource(R.string.login_title),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 54.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily.Cursive
            )

            EmailField(
                value = userName,
                onValueChange = onUsernameChanged,
                errorType = errorType
            )

            Spacer(modifier = Modifier.height(16.dp))

            PasswordField(
                value = password,
                onValueChange = onPasswordChanged,
                errorType = errorType
            )

            Spacer(modifier = Modifier.height(100.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .padding(horizontal = 32.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f),
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = RoundedCornerShape(4.dp),
                onClick = onLogin,
                enabled = !isLoginButtonActive
            ) {
                if (isLoginButtonActive) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(18.dp),
                        strokeWidth = 2.dp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(Modifier.width(10.dp))
                }
                Text(stringResource(R.string.login_button))
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                modifier = Modifier
                    .width(300.dp)
                    .height(40.dp),
                onClick = { },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                elevation = null
            ) {
                Text(
                    text = stringResource(R.string.login_create_account),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light,
                    letterSpacing = 0.5.sp
                )
            }
        }
    }
}

@Composable
fun EmailField(
    value: String,
    onValueChange: (String) -> Unit,
    errorType: ErrorType? = null
) {
    val showError = errorType == ErrorType.EMPTY_EMAIL || errorType == ErrorType.UNKNOWN_ERROR
    val errorText = when (errorType) {
        ErrorType.EMPTY_EMAIL -> stringResource(R.string.login_error_empty_email)
        ErrorType.UNKNOWN_ERROR -> stringResource(R.string.login_error_unknown)
        else -> null
    }

    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        label = {
            Text(
                text = stringResource(R.string.login_email_label),
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
            )
        },
        placeholder = {
            Text(
                text = stringResource(R.string.login_email_placeholder),
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f)
            )
        },
        singleLine = true,
        isError = showError,
        colors = TextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedIndicatorColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f),
            cursorColor = MaterialTheme.colorScheme.onPrimary,
            focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
        )
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp)
            .padding(start = 32.dp)
    ) {
        if (errorText != null) {
            Text(
                text = errorText,
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp,
                modifier = Modifier.align(Alignment.TopStart)
            )
        }
    }
}

@Composable
fun PasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    errorType: ErrorType? = null
) {
    val showError = errorType == ErrorType.EMPTY_PASSWORD || errorType == ErrorType.UNKNOWN_ERROR
    val errorText = when (errorType) {
        ErrorType.EMPTY_PASSWORD -> stringResource(R.string.login_error_empty_password)
        ErrorType.UNKNOWN_ERROR -> stringResource(R.string.login_error_unknown)
        else -> null
    }

    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        label = {
            Text(
                text = stringResource(R.string.login_password_label),
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
            )
        },
        placeholder = {
            Text(
                text = stringResource(R.string.login_password_placeholder),
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f)
            )
        },
        visualTransformation = PasswordVisualTransformation(),
        singleLine = true,
        isError = showError,
        colors = TextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedIndicatorColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f),
            cursorColor = MaterialTheme.colorScheme.onPrimary,
            focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
        )
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp)
            .padding(start = 32.dp)
    ) {
        if (errorText != null) {
            Text(
                text = errorText,
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp,
                modifier = Modifier.align(Alignment.TopStart)
            )
        }
    }
}

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onBack: () -> Unit,
    viewModel: LoginViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val onUsernameChanged = remember(viewModel) { { s: String -> viewModel.onUsernameChanged(s) } }
    val onPasswordChanged = remember(viewModel) { { s: String -> viewModel.onPasswordChanged(s) } }
    val onLogin = remember(viewModel) { { viewModel.onLogin(); Unit } }

    LaunchedEffect(viewModel) {
        viewModel.events.collect { event ->
            when (event) {
                is LoginUiEvent.LoginSuccessEvent -> {
                    onLoginSuccess()
                }
            }
        }
    }

    LoginScreenContent(
        userName = state.userName,
        password = state.password,
        isLoginButtonActive = state.isLoginButtonActive,
        errorType = state.errorType,
        onBack = onBack,
        onUsernameChanged = onUsernameChanged,
        onPasswordChanged = onPasswordChanged,
        onLogin = onLogin
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    MaterialTheme {
        LoginScreenContent(
            userName = "gfgfg",
            password = "test",
            isLoginButtonActive = false,
            errorType = null,
            onBack = { },
            onUsernameChanged = { },
            onPasswordChanged = { },
            onLogin = { }
        )
    }
}