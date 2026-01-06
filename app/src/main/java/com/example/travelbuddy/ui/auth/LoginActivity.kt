package com.example.travelbuddy.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.travelbuddy.data.remote.ApiClient
import com.example.travelbuddy.data.remote.api.ApiClient
import com.example.travelbuddy.data.remote.auth.AuthApiClient
import com.example.travelbuddy.databinding.ActivityLoginBinding
import com.example.travelbuddy.ui.main.MainActivity
import com.example.travelbuddy.utils.SessionManager
import com.example.travelbuddy.utils.Validators
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var session: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        session = SessionManager(this)
        val api = AuthApiClient.apiService

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString()

            if (!Validators.isValidEmail(email)) {
                showError("Por favor insira um e-mail válido.")
                return@setOnClickListener
            }

            if (!Validators.isValidPassword(password)) {
                showError("Password tem de ter pelo menos 8 caracteres.")
                return@setOnClickListener
            }

            lifecycleScope.launch {
                try {
                    val response = api.login(
                        mapOf(
                            "email" to email,
                            "password" to password
                        )
                    )

                    session.saveSession(
                        email = response.email,
                        token = response.token
                    )

                    Toast.makeText(
                        this@LoginActivity,
                        "Login efetuado com sucesso",
                        Toast.LENGTH_SHORT
                    ).show()

                    startActivity(
                        Intent(this@LoginActivity, MainActivity::class.java)
                    )
                    finish()

                } catch (e: Exception) {
                    showError("Erro no login")
                }
            }
        }
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
