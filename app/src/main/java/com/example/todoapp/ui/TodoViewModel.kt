package com.example.todoapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.CurrentUserHolder
import com.example.todoapp.data.UserDatabaseRepository
import com.example.todoapp.model.Activities
import com.example.todoapp.model.ApplicationUser
import com.example.todoapp.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val userDatabaseRepository: UserDatabaseRepository
    ) : ViewModel() {

    private val _additionState = MutableStateFlow<UiState<*>>(UiState.Empty)
    val additionState: StateFlow<UiState<*>> get() = _additionState

    private val _activitiesState = MutableStateFlow<List<Activities>>(emptyList())
    val activitiesState: StateFlow<List<Activities>> get() = _activitiesState

    init {
        viewModelScope.launch {
            _activitiesState.value = CurrentUserHolder.getCurrentUser()?.listOfActivities ?: emptyList()
        }
    }


    fun updateItemStatus(applicationUser: ApplicationUser, position: Int) {
        if (position >= 0 && position < applicationUser.listOfActivities.size) {
            val updatedActivity = applicationUser.listOfActivities[position]
            updatedActivity.isDone = if (updatedActivity.isDone == 1) 0 else 1
            applicationUser.listOfActivities[position] = updatedActivity

            viewModelScope.launch {
                try {
                    userDatabaseRepository.updateUserActivityStatus(applicationUser)
                } catch (e: Exception) {
                    println(e.message)
                }
            }
        }
    }


    fun addItemToRoomDatabase(item: String) {
        viewModelScope.launch {
            _additionState.value = UiState.Loading
            try {
                userDatabaseRepository.addActivityItem(item)
                _additionState.value = UiState.Success(true)
                _activitiesState.value = CurrentUserHolder.getCurrentUser()?.listOfActivities ?: emptyList()
            } catch (e: Exception) {
                _additionState.value = UiState.Error("Failed to add item: ${e.message}")
            }
        }
    }
}