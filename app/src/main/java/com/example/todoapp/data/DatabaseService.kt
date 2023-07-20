package com.example.todoapp.data

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.example.todoapp.MainActivity
import com.example.todoapp.model.Activities
import com.example.todoapp.model.ApplicationUser

object DatabaseService {
    private var appDatabase: UserDatabase? = null
    private var currentUser: ApplicationUser? = null

    fun init(context: Context) {
        appDatabase = UserDatabase.getInstance(context)
    }

    fun getUserDao(): UserDao {
        return appDatabase?.userDao() ?: throw IllegalStateException("Database not initialized.")
    }

    suspend fun registerUser(user: ApplicationUser) {
        val userDao = getUserDao()
        userDao.insertUser(user)
    }


    suspend fun loginUser(mail:String,password:String): ApplicationUser{
        val userDao = getUserDao()
        currentUser = userDao.getUser(mail,password)!!
        return currentUser as ApplicationUser
    }

    fun getCurrentUser(): ApplicationUser? {
        return currentUser
    }

    suspend fun addActivityElement(element: String) {
        val userDao = getUserDao()

        currentUser?.let {
            val updatedActivities = it.listOfActivities.toMutableList()

            val newActivity = Activities(activity = element)
            updatedActivities.add(newActivity)

            it.listOfActivities = updatedActivities
            userDao.updateUser(it)
        }
    }
    suspend fun updateUserActivityStatus(user: ApplicationUser) {
        val userDao = getUserDao()
        userDao.updateUser(user)
    }
}