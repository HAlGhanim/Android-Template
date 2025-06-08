package com.example.androidtemplate.utils

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.androidtemplate.network.AuthApiService
import com.example.androidtemplate.network.RetrofitHelper
import com.example.androidtemplate.viewmodels.AuthViewModel

object AppInitializer {

    fun provideTokenManager(context: Context): TokenManager {
        return TokenManager.create(context)
    }

    fun provideAuthApiService(tokenManager: TokenManager): AuthApiService {
        val retrofit = RetrofitHelper.getInstance(tokenManager)
        return retrofit.create(AuthApiService::class.java)
    }

    fun provideAuthViewModelFactory(context: Context): ViewModelProvider.Factory {
        val tokenManager = provideTokenManager(context)
        val apiService = provideAuthApiService(tokenManager)

        return viewModelFactory {
            initializer {
                AuthViewModel(apiService, tokenManager)
            }
        }
    }
}
