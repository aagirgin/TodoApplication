package com.example.todoapp.data

import com.example.todoapp.domain.model.ApplicationUser
import com.example.todoapp.domain.model.UserActivities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserDatabaseRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : UserDatabaseRepository {

    override suspend fun registerUser(user: ApplicationUser) {
        userDao.insertUser(user)
    }

    override suspend fun getUser(email: String, pass: String): ApplicationUser? {
        val currentUser = userDao.getUser(email, pass)
        CurrentUserHolder.setCurrentUser(currentUser)
        return currentUser
    }

    override suspend fun addActivityItem(activityItem: String) {
        CurrentUserHolder.getCurrentUser()?.let { currentUser ->
            val updatedActivities = currentUser.listOfActivities.toMutableList()
            val newActivity = UserActivities(activityName = activityItem)
            updatedActivities.add(newActivity)
            currentUser.listOfActivities = updatedActivities
            userDao.updateUser(currentUser)
        }
    }

    override suspend fun updateUserActivityStatus(user: ApplicationUser) {
        userDao.updateUser(user)
    }

    override suspend fun checkEmailExists(email: String): Boolean {
        return withContext(Dispatchers.IO) {
            userDao.getUserByEmail(email) != null
        }
    }
}