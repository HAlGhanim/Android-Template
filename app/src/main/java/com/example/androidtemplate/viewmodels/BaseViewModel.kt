package com.example.androidtemplate.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {
    protected val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> get() = _isLoading

    protected val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> get() = _errorMessage

    protected fun setLoading(value: Boolean) {
        _isLoading.value = value
    }

    protected fun setError(message: String?) {
        _errorMessage.value = message
    }

    protected fun clearError() {
        _errorMessage.value = null
    }
}
