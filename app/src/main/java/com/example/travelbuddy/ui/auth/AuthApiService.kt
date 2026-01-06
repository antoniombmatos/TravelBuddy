package com.example.travelbuddy.data.remote.auth

import com.example.travelbuddy.data.remote.auth.model.AuthResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("auth/login")
    suspend fun login(
        @Body credentials: Map<String, String>
    ): AuthResponse
}
