package com.example.todoapp.ui

import androidx.lifecycle.ViewModel
import com.example.todoapp.data.DatabaseService
import com.example.todoapp.model.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.lang.Exception

class AdditionViewModel : ViewModel(){
    private val _additionState = MutableStateFlow<UiState<*>>(UiState.Empty)
    val additionState: StateFlow<UiState<*>> get() = _additionState


}