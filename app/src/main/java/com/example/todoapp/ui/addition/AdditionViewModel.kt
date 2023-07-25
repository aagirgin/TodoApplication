package com.example.todoapp.ui.addition

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.R
import com.example.todoapp.data.UserDatabaseRepository
import com.example.todoapp.domain.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject
@HiltViewModel
class AdditionViewModel @Inject constructor(
                        private val userDatabaseRepository: UserDatabaseRepository
) : ViewModel(){

    private val _additionState = MutableStateFlow<UiState<*>>(UiState.Empty)
    val additionState: StateFlow<UiState<*>> get() = _additionState
    suspend fun addItemIntoUser(activityItem:String) {
        viewModelScope.launch {
            _additionState.value = UiState.Loading
            try {
                val isAddedSuccessfully = userDatabaseRepository.addActivityItem(activityItem)
                if(isAddedSuccessfully){
                    _additionState.value = UiState.Success(true)
                }
                else{
                    _additionState.value = UiState.Error(R.string.item_add_fail.toString())
                }
            } catch (e: Exception) {
                _additionState.value = UiState.Error("${R.string.item_add_fail.toString()} ${e.message}")
            }
        }

    }
}