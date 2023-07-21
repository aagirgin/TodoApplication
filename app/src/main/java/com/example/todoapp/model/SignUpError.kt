package com.example.todoapp.model

sealed interface SignUpError {
    object InvalidEmail : SignUpError {
        override val message: String
            get() = "Invalid email format"
    }

    object PasswordMismatch : SignUpError {
        override val message: String
            get() = "Passwords do not match"
    }

    object EmailExists : SignUpError {
        override val message: String
            get() = "Email already exists"
    }

    object BlankItem : SignUpError {
        override val message: String
            get() = "Please fill in all fields"
    }

    val message: String
}