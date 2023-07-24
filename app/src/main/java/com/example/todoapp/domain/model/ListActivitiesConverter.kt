package com.example.todoapp.domain.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListActivitiesConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromListActivities(list: List<UserActivities>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toListActivities(json: String): List<UserActivities> {
        val type = object : TypeToken<List<UserActivities>>() {}.type
        return gson.fromJson(json, type)
    }
}