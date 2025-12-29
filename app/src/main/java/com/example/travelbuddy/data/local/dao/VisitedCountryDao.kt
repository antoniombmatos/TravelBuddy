package com.example.travelbuddy.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.travelbuddy.data.local.entity.VisitedCountryEntity

@Dao
interface VisitedCountryDao {

    @Query("SELECT * FROM visited_countries")
    suspend fun getAllVisited(): List<VisitedCountryEntity>

    @Query("SELECT countryCode FROM visited_countries")
    suspend fun getVisitedCountryCodes(): List<String>

    @Insert
    suspend fun insert(visitedCountry: VisitedCountryEntity)
}
