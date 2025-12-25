package com.example.travelbuddy.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.travelbuddy.data.local.dao.CountryDao
import com.example.travelbuddy.data.local.dao.UserDao
import com.example.travelbuddy.data.local.dao.VisitedCountryDao
import com.example.travelbuddy.data.local.entity.CountryEntity
import com.example.travelbuddy.data.local.entity.UserEntity
import com.example.travelbuddy.data.local.entity.VisitedCountryEntity

@Database(
    entities = [
        UserEntity::class,
        CountryEntity::class,
        VisitedCountryEntity::class
    ],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun countryDao(): CountryDao
    abstract fun visitedCountryDao(): VisitedCountryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "travelbuddy_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
