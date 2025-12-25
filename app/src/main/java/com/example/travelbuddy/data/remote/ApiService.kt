package com.example.travelbuddy.data.remote

import com.example.travelbuddy.data.local.entity.UserEntity
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    // Rota para Login
    // Envia um MAP (JSON) com email/password e recebe um UserEntity se tiver sucesso
    @POST("auth/login")
    suspend fun login(@Body credentials: Map<String, String>): UserEntity?

    // Rota para Registar
    @POST("auth/register")
    suspend fun register(@Body user: UserEntity): UserEntity?
}