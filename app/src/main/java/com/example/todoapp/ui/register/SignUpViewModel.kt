package com.example.todoapp.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.UserDatabaseRepository
import com.example.todoapp.domain.model.ApplicationUser
import com.example.todoapp.domain.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel@Inject constructor(
    private val userDatabaseRepository: UserDatabaseRepository
):ViewModel() {
    private val _registerState = MutableStateFlow<UiState<*>>(UiState.Empty)
    val registerState: StateFlow<UiState<*>> get() = _registerState


    private val _emailExistsState = MutableStateFlow<UiState<Boolean>>(UiState.Empty)
    val emailExistsState: StateFlow<UiState<Boolean>> get() = _emailExistsState

    suspend fun checkEmailExists(email: String): Boolean {
        return try {
            userDatabaseRepository.checkEmailExists(email)
        } catch (e: Exception) {
            return true
        }
    }
    fun signUpUser(user: ApplicationUser) {
        viewModelScope.launch {
            _registerState.value = UiState.Loading
            try {
                userDatabaseRepository.registerUser(user)
                _registerState.value = UiState.Success(true)
            } catch (e: Exception) {
                _registerState.value = UiState.Error("Failed to sign up: ${e.message}")
            }
        }
    }


}
