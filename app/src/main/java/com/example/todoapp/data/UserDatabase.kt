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

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getInstance(context: Context): UserDatabase {
            return INSTANCE ?: synchronized(this) {
                val migration1to2 = object : Migration(2, 3) {
                    override fun migrate(database: SupportSQLiteDatabase) {
                        database.execSQL("ALTER TABLE users RENAME TO temp_users")
                        database.execSQL("CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, mail TEXT, password TEXT, fullname TEXT)")
                        database.execSQL("INSERT INTO users SELECT id, mail, password, fullname FROM temp_users")
                        database.execSQL("DROP TABLE temp_users")

                        // Add the column again
                        database.execSQL("ALTER TABLE users ADD COLUMN listOfActivities TEXT")
                    }
                }

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user-database"
                )
                    .addMigrations(migration1to2) // Add the migration here
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}