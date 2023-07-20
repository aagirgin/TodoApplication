package com.example.todoapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.DatabaseService
import com.example.todoapp.model.ApplicationUser
import com.example.todoapp.model.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignUpViewModel:ViewModel() {
    private val _registerState = MutableStateFlow<UiState<*>>(UiState.Empty)
    val registerState: StateFlow<UiState<*>> get() = _registerState



    fun signUpUser(user: ApplicationUser) {
        viewModelScope.launch {
            _registerState.value = UiState.Loading

            try {
                DatabaseService.registerUser(user)
                _registerState.value = UiState.Success(true)
            } catch (e: Exception) {
                _registerState.value = UiState.Error("Failed to sign up: ${e.message}")
            }
        }
    }
}
