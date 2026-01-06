package com.example.travelbuddy.data.remote.auth

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AuthApiClient {

    private const val BASE_URL = "http://10.0.2.2:8000/"
    // API Python do teu colega

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: AuthApiService by lazy {
        retrofit.create(AuthApiService::class.java)
    }
}
