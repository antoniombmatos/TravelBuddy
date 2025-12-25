package com.example.travelbuddy.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "countries")
data class CountryEntity(
    @PrimaryKey
    val isoCode: String,      // PT, ES, FR, etc.
    val name: String,
    val continent: String,
    val flagUrl: String
)
