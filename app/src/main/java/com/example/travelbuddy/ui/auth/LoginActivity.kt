package com.example.travelbuddy.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.travelbuddy.data.local.AppDatabase
import com.example.travelbuddy.databinding.ActivityLoginBinding
import com.example.travelbuddy.utils.SessionManager
import com.example.travelbuddy.utils.Validators
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = AppDatabase.getInstance(this)
        val session = SessionManager(this)

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
                val user = db.userDao().getByEmail(email)

                if (user == null || user.passwordHash != password) {
                    showError("Email ou password inválidos.")
                } else {
                    session.saveUser(email)
                    Toast.makeText(
                        this@LoginActivity,
                        "Login efetuado com sucesso",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
