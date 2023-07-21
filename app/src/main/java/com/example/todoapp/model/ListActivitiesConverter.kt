package com.example.todoapp.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListActivitiesConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromListActivities(list: List<Activities>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toListActivities(json: String): List<Activities> {
        val type = object : TypeToken<List<Activities>>() {}.type
        return gson.fromJson(json, type)
    }
}