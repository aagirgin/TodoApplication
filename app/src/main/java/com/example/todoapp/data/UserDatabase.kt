package com.example.todoapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.todoapp.model.ApplicationUser
import com.example.todoapp.model.ListActivitiesConverter


@Database(entities = [ApplicationUser::class], version = 3)
@TypeConverters(ListActivitiesConverter::class)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

}