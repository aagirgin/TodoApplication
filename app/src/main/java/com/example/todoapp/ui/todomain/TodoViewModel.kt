package com.example.todoapp.ui.todomain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.R
import com.example.todoapp.data.CurrentUserHolder
import com.example.todoapp.data.UserDatabaseRepository
import com.example.todoapp.domain.model.ApplicationUser
import com.example.todoapp.domain.model.CompletedStatus
import com.example.todoapp.domain.model.UserActivities
import com.example.todoapp.domain.state.UiState
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

    private val _activitiesState = MutableStateFlow<List<UserActivities>>(emptyList())
    val activitiesState: StateFlow<List<UserActivities>> get() = _activitiesState

    init {
        viewModelScope.launch {
            _activitiesState.value = CurrentUserHolder.getCurrentUser()?.listOfActivities ?: emptyList()
        }
    }


    fun updateItemStatus(applicationUser: ApplicationUser, position: Int) {
        if (position >= 0 && position < applicationUser.listOfActivities.size) {
            val updatedActivity = applicationUser.listOfActivities[position]
            updatedActivity.isDone = if (updatedActivity.isDone == CompletedStatus.COMPLETED) CompletedStatus.INCOMPLETE else CompletedStatus.COMPLETED
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
                val isAddedSuccessfully = userDatabaseRepository.addActivityItem(item)
                if (isAddedSuccessfully) {
                    _additionState.value = UiState.Success(true)
                    _activitiesState.value = CurrentUserHolder.getCurrentUser()?.listOfActivities ?: emptyList()
                } else {
                    _additionState.value = UiState.Error(R.string.item_add_fail.toString())
                }
            } catch (e: Exception) {
                _additionState.value = UiState.Error("${R.string.item_add_fail.toString()} ${e.message}")
            }
        }
    }
}