package com.example.todoapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "users")
@TypeConverters(ListActivitiesConverter::class)
data class ApplicationUser(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val mail: String,
    val password: String,
    val fullname: String,
    @ColumnInfo(name = "listOfActivities")
    var listOfActivities: MutableList<Activities> = mutableListOf()
)
data class Activities(
    var isDone: Int = 0,
    val activity: String
)


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