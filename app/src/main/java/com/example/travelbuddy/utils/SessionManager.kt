package com.example.travelbuddy.utils

import android.content.Context

class SessionManager(context: Context) {

    private val prefs =
        context.getSharedPreferences("travelbuddy_session", Context.MODE_PRIVATE)

    fun saveSession(email: String, token: String) {
        prefs.edit()
            .putString("user_email", email)
            .putString("jwt_token", token)
            .apply()
    }

    fun getUserEmail(): String? =
        prefs.getString("user_email", null)

    fun getToken(): String? =
        prefs.getString("jwt_token", null)

    fun logout() {
        prefs.edit().clear().apply()
    }
}
