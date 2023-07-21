package com.example.todoapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todoapp.domain.model.ApplicationUser
import com.example.todoapp.domain.model.ListActivitiesConverter


@Database(entities = [ApplicationUser::class], version = 3)
@TypeConverters(ListActivitiesConverter::class)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

}