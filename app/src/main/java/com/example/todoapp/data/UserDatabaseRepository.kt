package com.example.todoapp.data

import com.example.todoapp.domain.model.ApplicationUser

interface UserDatabaseRepository {

    suspend fun registerUser(user: ApplicationUser): Boolean

    suspend fun getUser(email: String, pass: String): ApplicationUser?

    suspend fun addActivityItem(activityItem: String) : Boolean

    suspend fun updateUserActivityStatus(user: ApplicationUser)

    suspend fun checkEmailExists(email: String): Boolean

}