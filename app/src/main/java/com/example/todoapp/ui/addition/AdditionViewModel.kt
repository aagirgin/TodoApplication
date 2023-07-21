package com.example.todoapp.ui.addition

import androidx.lifecycle.ViewModel
import com.example.todoapp.data.UserDatabaseRepository
import com.example.todoapp.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.lang.Exception
import javax.inject.Inject
@HiltViewModel
class AdditionViewModel @Inject constructor(
                        private val userDatabaseRepository: UserDatabaseRepository
) : ViewModel(){

    private val _additionState = MutableStateFlow<UiState<*>>(UiState.Empty)
    val additionState: StateFlow<UiState<*>> get() = _additionState
    suspend fun addItemIntoUser(activityItem:String) {
        try {
            _additionState.value = UiState.Loading
            userDatabaseRepository.addActivityItem(activityItem)
            _additionState.value = UiState.Success("Activity item added successfully.")
        } catch (e: Exception) {
            _additionState.value = e.message?.let { UiState.Error(it) }!!
        }
    }



}