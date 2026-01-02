package com.example.travelbuddy.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "countries")
data class CountryEntity(
    @PrimaryKey
    val code: String, // ex: "PT", "ES", "FR"

    val name: String,
    val flagUrl: String? = null
)
