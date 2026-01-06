package com.example.travelbuddy.data.remote.auth.model

data class AuthResponse(
    val id: Int,
    val name: String,
    val email: String,
    val token: String
)