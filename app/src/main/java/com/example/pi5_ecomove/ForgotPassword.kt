package com.example.pi5_ecomove

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ForgotPassword : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        // Referenciando os elementos da interface
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val submitButton = findViewById<Button>(R.id.submitButton)
        val cancelButton = findViewById<Button>(R.id.cancelButton)

        // Botão para enviar a solicitação de redefinição de senha
        submitButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()

            if (email.isEmpty()) {
                Toast.makeText(this, "Digite o e-mail", Toast.LENGTH_SHORT).show()
            } else {
                val retrofit = Retrofit.Builder()
                    .baseUrl("http://192.168.10.26") // Certifique-se de que a URL está correta
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val apiService = retrofit.create(ApiService::class.java)
                val call = apiService.validateEmail(email)

                // Enfileirar a chamada de API
                call.enqueue(object : Callback<ApiResponse> {
                    override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                        if (response.isSuccessful && response.body()?.status == "success") {
                            Toast.makeText(this@ForgotPassword, "E-mail válido!", Toast.LENGTH_SHORT).show()

                            // Redireciona para a tela de atualização de senha
                            val intent = Intent(this@ForgotPassword, UpdatePasswordActivity::class.java)
                            intent.putExtra("email", email) // Passa o e-mail para a próxima tela
                            startActivity(intent)
                            finish() // Finaliza a tela atual
                        } else {
                            Toast.makeText(this@ForgotPassword, "E-mail não encontrado", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                        Toast.makeText(this@ForgotPassword, "Erro na conexão: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
        // Botão para cancelar e voltar para a tela de login
        cancelButton.setOnClickListener {
            finish() // Encerra esta tela e volta à anterior
        }
    }
}
