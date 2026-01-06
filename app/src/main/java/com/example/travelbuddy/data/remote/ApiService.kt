package com.example.travelbuddy.data.remote

import com.example.travelbuddy.data.remote.model.AuthResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("auth/login")
    suspend fun login(
        @Body credentials: Map<String, String>
    ): AuthResponse

    @POST("auth/register")
    suspend fun register(
        @Body user: Map<String, String>
    ): AuthResponse
}
