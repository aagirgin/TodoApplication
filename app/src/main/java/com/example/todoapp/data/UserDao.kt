package com.example.todoapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.todoapp.domain.model.ApplicationUser

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE mail = :email AND password = :password")
    suspend fun getUser(email: String, password: String): ApplicationUser?

    @Query("SELECT * FROM users WHERE mail = :email")
    suspend fun getUserByEmail(email: String): ApplicationUser?
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: ApplicationUser): Long

    @Update
    suspend fun updateUser(user: ApplicationUser)

}