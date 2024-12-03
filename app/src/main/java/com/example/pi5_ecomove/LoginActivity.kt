package com.example.pi5_ecomove

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
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

        // Referenciando os campos corretamente
        val usernameEditText = findViewById<EditText>(R.id.usernameEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val registerButton = findViewById<Button>(R.id.registerButton)

        // Botão de Cadastro
        registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // Configurando ação do botão de login
        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            // Verificar se os campos estão preenchidos
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    this,
                    "Por favor, preencha todos os campos!",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            // Implementar lógica de autenticação
            blockLogin(username, password)
        }
    }

    private fun blockLogin(username: String, password: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.15.162/") // Atualize conforme necessário
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        val call = apiService.login(username, password)
        call.enqueue(object : Callback<List<LoginResponse>> {
            override fun onResponse(
                call: Call<List<LoginResponse>>,
                response: Response<List<LoginResponse>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val loginResponses = response.body()!!
                    if (loginResponses.isNotEmpty()) {
                        // Redireciona para a HomeActivity (ou a tela correta)
                        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            "Usuário ou senha inválidos",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        "Erro no login",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<LoginResponse>>, t: Throwable) {
                Toast.makeText(
                    this@LoginActivity,
                    "Erro: ${t.message}",
                    Toast.LENGTH_LONG
                ).show()
                Log.e("LoginError", "Erro de conexão: ${t.message}", t)
            }
        })
    }
}
