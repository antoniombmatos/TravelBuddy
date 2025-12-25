package com.example.travelbuddy.data.remote.model

data class CountryDto(
    val name: Name,
    val cca2: String,
    val continents: List<String>,
    val flags: Flags
) {
    data class Name(val common: String)
    data class Flags(val png: String)
}