package com.example.pi5_ecomove

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val usernameEditText = findViewById<EditText>(R.id.usernameEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val registerButton = findViewById<Button>(R.id.registerButton) // Botão de cadastrar-se
        val forgotPasswordTextView = findViewById<TextView>(R.id.forgotPasswordTextView) // Texto para "Esqueceu a senha"

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            doLogin(username, password)
        }

        registerButton.setOnClickListener {
            // Navegar para a tela de registro
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

        forgotPasswordTextView.setOnClickListener {
            // Navegar para a tela de "Esqueceu a senha"
            val intent = Intent(this@LoginActivity, ForgotPassword::class.java)
            startActivity(intent)
        }
    }

    private fun doLogin(username: String, password: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.15.61/") // Certifique-se de que o IP está correto
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        val call = apiService.login(username, password)

        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse != null && loginResponse.erro == null) {
                        Toast.makeText(
                            this@LoginActivity,
                            "Bem-vindo, ${loginResponse.nome_completo}!",
                            Toast.LENGTH_SHORT
                        ).show()

                        // Navegar para a próxima tela
                        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        val errorMessage = loginResponse?.erro ?: "Usuário ou senha inválidos."
                        Toast.makeText(this@LoginActivity, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        "Erro ao conectar: ${response.code()} - ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(
                    this@LoginActivity,
                    "Erro de conexão: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}
