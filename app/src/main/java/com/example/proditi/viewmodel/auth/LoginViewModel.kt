package com.example.proditi.viewmodel.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proditi.modeldata.LoginRequest
import com.example.proditi.repositori.PeminjamanRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface LoginUiState {
    object Idle : LoginUiState
    object Loading : LoginUiState
    object Success : LoginUiState
    data class Error(val message: String) : LoginUiState
}

class LoginViewModel(private val repository: PeminjamanRepository) : ViewModel() {
    var uiState: LoginUiState by mutableStateOf(LoginUiState.Idle)
        private set

    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set

    fun updateEmail(input: String) { email = input }
    fun updatePassword(input: String) { password = input }

    fun login(onLoginSuccess: () -> Unit) {
        viewModelScope.launch {
            uiState = LoginUiState.Loading
            try {
                val response = repository.login(LoginRequest(email, password))
                if (response.status) {
                    uiState = LoginUiState.Success
                    onLoginSuccess() // Navigasi ke Dashboard
                } else {
                    uiState = LoginUiState.Error(response.message)
                }
            } catch (e: Exception) {
                uiState = LoginUiState.Error(
                    when (e) {
                        is IOException -> "Kesalahan Jaringan"
                        is HttpException -> "Email atau Password Salah"
                        else -> e.message ?: "Unknown Error"
                    }
                )
            }
        }
    }
}