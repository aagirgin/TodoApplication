package com.example.todoapp.ui.login

import androidx.lifecycle.ViewModel
import com.example.todoapp.data.UserDatabaseRepository
import com.example.todoapp.domain.model.ApplicationUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userDatabaseRepository: UserDatabaseRepository) :ViewModel() {



    private val _currentUserState = MutableStateFlow<ApplicationUser?>(null)
    val currentUserState: StateFlow<ApplicationUser?> get() = _currentUserState

    suspend fun loginUser(mail: String, pass: String) {
        val currentUser = userDatabaseRepository.getUser(mail,pass)
        _currentUserState.value = currentUser
    }

}