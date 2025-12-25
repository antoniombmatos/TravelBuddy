package com.example.travelbuddy.data.remote.repository

import com.example.travelbuddy.data.remote.api.ApiClient
import com.example.travelbuddy.data.remote.api.CountriesApi

class CountryRepository {

    private val api = ApiClient.retrofit.create(CountriesApi::class.java)

    suspend fun getCountries() = api.getAllCountries()
}
