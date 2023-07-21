package com.example.todoapp.data

import com.example.todoapp.domain.model.ApplicationUser

object CurrentUserHolder {
    private var currentUser: ApplicationUser? = null

    fun setCurrentUser(user: ApplicationUser?) {
        currentUser = user
    }

    fun getCurrentUser(): ApplicationUser? {
        return currentUser
    }
}