package com.example.minicafegame.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minicafegame.network.ApiClient
import com.example.minicafegame.network.LoginRequest
import com.example.minicafegame.network.RegisterRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val token: String, val username: String) : AuthState()
    data class Error(val message: String) : AuthState()
}

class AuthViewModel : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val response = ApiClient.api.login(LoginRequest(email, password))
                _authState.value = AuthState.Success(response.token, response.username)
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Giriş başarısız: ${e.message}")
            }
        }
    }

    fun register(username: String, email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                ApiClient.api.register(RegisterRequest(username, email, password))
                login(email, password)
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Kayıt başarısız: ${e.message}")
            }
        }
    }
}
