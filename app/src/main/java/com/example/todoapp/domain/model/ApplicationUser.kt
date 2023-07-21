package com.example.todoapp.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

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


