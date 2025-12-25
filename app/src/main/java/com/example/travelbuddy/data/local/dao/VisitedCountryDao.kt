package com.example.travelbuddy.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.travelbuddy.data.local.entity.VisitedCountryEntity

@Dao
interface VisitedCountryDao {

    @Insert
    suspend fun insert(visitedCountry: VisitedCountryEntity)

    @Query("SELECT countryIsoCode FROM visited_countries WHERE userId = :userId")
    suspend fun getVisitedCountries(userId: Int): List<String>
}
