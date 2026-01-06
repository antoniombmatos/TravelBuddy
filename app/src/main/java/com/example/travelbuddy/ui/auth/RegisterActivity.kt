package com.example.travelbuddy.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.travelbuddy.data.remote.ApiClient
import com.example.travelbuddy.databinding.ActivityRegisterBinding
import com.example.travelbuddy.ui.main.MainActivity
import com.example.travelbuddy.utils.SessionManager
import com.example.travelbuddy.utils.Validators
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var session: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        session = SessionManager(this)
        val api = ApiClient.apiService

        binding.btnRegister.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString()

            if (name.isEmpty()) {
                showError("Nome obrigatório")
                return@setOnClickListener
            }

            if (!Validators.isValidEmail(email)) {
                showError("Email inválido")
                return@setOnClickListener
            }

            if (!Validators.isValidPassword(password)) {
                showError("Password mínima: 8 caracteres")
                return@setOnClickListener
            }

            lifecycleScope.launch {
                try {
                    val response = api.register(
                        mapOf(
                            "name" to name,
                            "email" to email,
                            "password" to password
                        )
                    )

                    session.saveSession(
                        email = response.email,
                        token = response.token
                    )

                    Toast.makeText(
                        this@RegisterActivity,
                        "Registo efetuado com sucesso",
                        Toast.LENGTH_SHORT
                    ).show()

                    startActivity(
                        Intent(this@RegisterActivity, MainActivity::class.java)
                    )
                    finish()

                } catch (e: Exception) {
                    showError("Erro no registo")
                }
            }
        }
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
