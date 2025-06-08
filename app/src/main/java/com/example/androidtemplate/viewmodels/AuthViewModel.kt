package com.example.androidtemplate.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.androidtemplate.data.dtos.User
import com.example.androidtemplate.data.responses.TokenResponse
import com.example.androidtemplate.network.AuthApiService
import com.example.androidtemplate.utils.TokenManager
import kotlinx.coroutines.launch

class AuthViewModel(
    private val apiService: AuthApiService,
    private val tokenManager: TokenManager
) : BaseViewModel() {

    private val _token = mutableStateOf<TokenResponse?>(null)
    val token: State<TokenResponse?> get() = _token

    private val _user = mutableStateOf<User?>(null)
    val user: State<User?> get() = _user

    init {
        loadStoredToken()
    }

    private fun loadStoredToken() {
        val savedToken = tokenManager.getToken()
        _token.value = savedToken?.let { TokenResponse(it) }

        if (savedToken != null) {
            fetchCurrentUser()
        }
    }

    private fun fetchCurrentUser() {
        viewModelScope.launch {
            setLoading(true)
            try {
                val response = apiService.getCurrentUser()
                if (response.isSuccessful) {
                    _user.value = response.body()
                } else {
                    setError("Session invalid: ${response.message()}")
                    tokenManager.clearToken()
                    _token.value = null
                }
            } catch (e: Exception) {
                setError("Fetch user failed: ${e.message}")
            } finally {
                setLoading(false)
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            setLoading(true)
            try {
                val response = apiService.login(User(email, password))
                if (!response.isSuccessful) {
                    setError("Login failed: ${response.message()}")
                    return@launch
                }

                val token = response.body()?.token
                if (token.isNullOrBlank()) {
                    setError("Empty token")
                    return@launch
                }

                tokenManager.saveToken(token)
                _token.value = TokenResponse(token)

                fetchCurrentUser()
            } catch (e: Exception) {
                setError("Login error: ${e.message}")
            } finally {
                setLoading(false)
            }
        }
    }

    fun logout() {
        tokenManager.clearToken()
        _token.value = null
        _user.value = null
        clearError()
        setLoading(false)
    }
}

