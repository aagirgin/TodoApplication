package com.example.todoapp.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.R
import com.example.todoapp.data.CurrentUserHolder
import com.example.todoapp.data.UserDatabaseRepository
import com.example.todoapp.domain.model.ApplicationUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userDatabaseRepository: UserDatabaseRepository) :ViewModel() {

    sealed class LoginEvent{
        data class ErrorEvent(val message: String): LoginEvent()
        data class SuccessEvent(val message: String):LoginEvent()
    }


    init {
        if(isAuthenticated()){
            viewModelScope.launch {
                evenChannel.send(LoginEvent.SuccessEvent(R.string.adready_auth.toString()))
            }
        }
    }

    private val evenChannel = Channel<LoginEvent>()

    val eventFlow = evenChannel.receiveAsFlow()

    private val _currentUserState = MutableStateFlow<ApplicationUser?>(null)
    val currentUserState: StateFlow<ApplicationUser?> get() = _currentUserState

    suspend fun loginUser(mail: String, pass: String) {
        val currentUser = userDatabaseRepository.getUser(mail,pass)
        _currentUserState.value = currentUser
        viewModelScope.launch {
            if (currentUser != null){
                evenChannel.send(LoginEvent.SuccessEvent(R.string.success_message.toString()))
            }
            else{
                evenChannel.send(LoginEvent.ErrorEvent(R.string.not_success_message.toString()))
            }
        }

    }

    fun checkAuthenticationAndProceed() {
        if (isAuthenticated()) {
            viewModelScope.launch {
                evenChannel.send(LoginEvent.SuccessEvent(R.string.adready_auth.toString()))
            }
        }
    }
    private fun isAuthenticated(): Boolean{
        return CurrentUserHolder.getCurrentUser() != null
    }

}