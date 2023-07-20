package com.example.todoapp.ui

import androidx.lifecycle.ViewModel
import com.example.todoapp.data.DatabaseService
import com.example.todoapp.model.ApplicationUser
import com.example.todoapp.model.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.lang.Exception

class LoginViewModel:ViewModel() {
    private val _loginState = MutableStateFlow<UiState<*>>(UiState.Empty)
    val loginState: StateFlow<UiState<*>> get() = _loginState



    suspend fun loginUser(mail: String, pass: String) {
        _loginState.value = UiState.Loading

        try {
            val currentUser = DatabaseService.loginUser(mail, pass)

            if (currentUser.id != null) {
                _loginState.value = UiState.Success(currentUser)
            } else {
                _loginState.value = UiState.Error("Failed to sign up")
            }
        } catch (e: Exception) {
            _loginState.value = UiState.Error("Failed to sign up: ${e.message}")
            println(e.message)
        }
    }

}