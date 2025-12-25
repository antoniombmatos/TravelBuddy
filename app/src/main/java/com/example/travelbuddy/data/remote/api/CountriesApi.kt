package com.example.travelbuddy.data.remote.api

import com.example.travelbuddy.data.remote.model.CountryDto
import retrofit2.http.GET

interface CountriesApi {

    @GET("v3.1/all")
    suspend fun getAllCountries(): List<CountryDto>
}
