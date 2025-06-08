package com.example.androidtemplate.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtemplate.data.dtos.User
import com.example.androidtemplate.data.responses.TokenResponse
import com.example.androidtemplate.network.AuthApiService
import com.example.androidtemplate.utils.TokenManager
import kotlinx.coroutines.launch

class AuthViewModel(
    private val apiService: AuthApiService,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> get() = _errorMessage

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> get() = _isLoading

    private val _token = mutableStateOf<TokenResponse?>(null)
    val token: State<TokenResponse?> get() = _token

    private val _user = mutableStateOf<User?>(null)
    val user: State<User?> get() = _user

    fun resetState() {
        _token.value = null
        _user.value = null
        _errorMessage.value = null
        _isLoading.value = false
    }


    fun login(username: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val loginResponse = apiService.login(User(email = username, password = password))
                if (!loginResponse.isSuccessful) {
                    _errorMessage.value = "Login failed: ${loginResponse.message()}"
                    return@launch
                }

                val authResponse = loginResponse.body()
                val rawToken = authResponse?.token
                if (rawToken.isNullOrBlank()) {
                    _errorMessage.value = "Received empty token"
                    return@launch
                }

                tokenManager.saveToken(rawToken)
                _token.value = TokenResponse(rawToken)

                val userResponse = apiService.getCurrentUser()
                if (userResponse.isSuccessful) {
                    _user.value = userResponse.body()
                } else {
                    _errorMessage.value = "Failed to fetch user: ${userResponse.message()}"
                }

            } catch (e: Exception) {
                _errorMessage.value = "Unexpected error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun logout() {
        tokenManager.clearToken()
        resetState()
    }
}
