package com.example.todoapp.di

import android.content.Context
import androidx.room.Room
import com.example.todoapp.data.UserDao
import com.example.todoapp.data.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    fun provideUserDatabase(@ApplicationContext context: Context): UserDatabase {
        return Room
            .databaseBuilder(context, UserDatabase::class.java,"user-database")
            .build()
    }

    @Provides
    fun provideUserDao(userDatabase: UserDatabase): UserDao {
        return userDatabase.userDao()
    }
}