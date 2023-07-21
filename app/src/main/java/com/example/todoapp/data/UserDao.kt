package com.example.todoapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todoapp.model.ApplicationUser

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE mail = :email AND password = :password")
    suspend fun getUser(email: String, password: String): ApplicationUser?
    @Insert
    suspend fun insertUser(user: ApplicationUser)

    @Update
    suspend fun updateUser(user: ApplicationUser)

}