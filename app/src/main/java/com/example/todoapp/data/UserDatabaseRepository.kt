package com.example.todoapp.data

import com.example.todoapp.model.Activities
import com.example.todoapp.model.ApplicationUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserDatabaseRepository @Inject constructor(
    private val userDao: UserDao
) {

    suspend fun registerUser(user:ApplicationUser){
        userDao.insertUser(user)
    }

    suspend fun getUser(email:String,pass:String): ApplicationUser?{
        val currentUser = userDao.getUser(email,pass)
        CurrentUserHolder.setCurrentUser(currentUser)
        return currentUser
    }

    suspend fun addActivityItem(activityItem: String) {
        CurrentUserHolder.getCurrentUser()?.let { currentUser ->
            val updatedActivities = currentUser.listOfActivities.toMutableList()
            val newActivity = Activities(activity = activityItem)
            updatedActivities.add(newActivity)
            currentUser.listOfActivities = updatedActivities
            userDao.updateUser(currentUser)
        }
    }

    suspend fun updateUserActivityStatus(user: ApplicationUser) {
        userDao.updateUser(user)
    }

    suspend fun checkEmailExists(email: String): Boolean {
        return withContext(Dispatchers.IO) {
            userDao.getUserByEmail(email) != null
        }
    }


}