package com.example.travelbuddy.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "visited_countries")
data class VisitedCountryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val countryCode: String,
    val visitedAt: Long = System.currentTimeMillis()
)
