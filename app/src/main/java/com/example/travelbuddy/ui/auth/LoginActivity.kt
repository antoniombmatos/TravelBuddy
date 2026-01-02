package com.example.travelbuddy.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.travelbuddy.MainActivity
import com.example.travelbuddy.data.remote.RetrofitClient
import com.example.travelbuddy.databinding.ActivityLoginBinding
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() {
        // Botão de Entrar
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            // 1. Validação de Dados (Requisito Obrigatório)
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Preenche todos os campos!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 2. Autenticação via API (Retrofit)
            performLogin(email, password)
        }

        // Link para "Registar" (Por enquanto apenas avisa)
        binding.tvRegister.setOnClickListener {
            Toast.makeText(this, "Funcionalidade de Registo em breve!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun performLogin(email: String, pass: String) {
        // Usamos lifecycleScope para fazer o pedido em background (Coroutines)
        lifecycleScope.launch {
            try {
                // Cria o JSON para enviar
                val credentials = mapOf("email" to email, "password" to pass)

                // CHAMA O SERVIDOR (O teu Python)
                val response = RetrofitClient.apiService.login(credentials)

                if (response != null) {
                    // SUCESSO!
                    Toast.makeText(this@LoginActivity, "Bem-vindo, ${response.name}!", Toast.LENGTH_LONG).show()

                    // Vai para o ecrã principal
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish() // Fecha o Login para não poder voltar atrás
                } else {
                    Toast.makeText(this@LoginActivity, "Login falhou.", Toast.LENGTH_SHORT).show()
                }

            } catch (e: Exception) {
                // ERRO DE REDE (Servidor desligado ou IP errado)
                Toast.makeText(this@LoginActivity, "Erro de conexão: ${e.message}", Toast.LENGTH_LONG).show()
                e.printStackTrace()
            }
        }
    }
}