package com.example.todoapp.domain.state

sealed interface LogInError{

    object InvalidCredentials : LogInError {
        override val message: String
            get() = "Please check your credentials."
    }

    object BlankItem : LogInError {
        override val message: String
            get() = "Please fill in all fields."
    }

    val message: String

}