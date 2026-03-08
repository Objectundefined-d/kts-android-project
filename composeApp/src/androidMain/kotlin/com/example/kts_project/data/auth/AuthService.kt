package com.example.kts_project.data.auth

import android.content.Context
import android.content.Intent
import android.net.Uri
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import javax.inject.Inject
import androidx.core.net.toUri
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.ClientSecretBasic
import net.openid.appauth.ResponseTypeValues


class AuthService @Inject constructor(
    private val context: Context
) {
    private val authService = AuthorizationService(context)

    private val serviceConfig = AuthorizationServiceConfiguration(
        "https://stepik.org/oauth2/authorize/".toUri(),
        "https://stepik.org/oauth2/token/".toUri()
    )

    fun getAuthRequest(): AuthorizationRequest {
        return AuthorizationRequest.Builder(
            serviceConfig,
            CLIENT_ID,
            ResponseTypeValues.CODE,
            REDIRECT_URI.toUri()
        ).build()
    }

    fun getAuthIntent(request: AuthorizationRequest) : Intent {
        return authService.getAuthorizationRequestIntent(request)
    }

    fun performTokenRequest(
        response: AuthorizationResponse,
        onSuccess: (accessToken: String, refreshToken: String?) -> Unit,
        onError: (Exception) -> Unit
    ) {
        val tokenRequest = response.createTokenExchangeRequest()
        val clientAuth = ClientSecretBasic(CLIENT_SECRET)

        authService.performTokenRequest(tokenRequest, clientAuth) { tokenResponse, ex ->
            if (tokenResponse != null) {
                TokenStorage.accessToken = tokenResponse.accessToken
                TokenStorage.refreshToken = tokenResponse.refreshToken
                onSuccess(tokenResponse.accessToken ?: "", tokenResponse.refreshToken)
            } else {
                onError(ex ?: Exception("Unknown error"))
            }
        }
    }


    companion object {
        const val CLIENT_ID = "jGOfGdZPCMAZ0aBiPpYFGt2wZajphDuxFS9oZwsy"
        const val CLIENT_SECRET = "CjqjqXbDBWY3aWeieFrRHDrJFrANbZSQvGCrlOSPtgUcT1IKtv27ib2oA8YN57h1udhK6V1CfDKXWPkLT8345HSGvHQo6jEl7qJCmXd7HObAab1O45alEvuCQzgZBsX3"
        const val REDIRECT_URI = "http://localhost"
    }
}