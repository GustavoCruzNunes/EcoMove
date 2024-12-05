package com.example.pi5_ecomove

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class UpdatePasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_password)

        val newPasswordEditText = findViewById<EditText>(R.id.newPasswordEditText)
        val confirmPasswordEditText = findViewById<EditText>(R.id.confirmPasswordEditText)
        val updatePasswordButton = findViewById<Button>(R.id.updatePasswordButton)
        val cancelButton = findViewById<Button>(R.id.cancelButton)

        // Pegando o email passado da tela anterior
        val email = intent.getStringExtra("email") ?: ""

        updatePasswordButton.setOnClickListener {
            val newPassword = newPasswordEditText.text.toString().trim()
            val confirmPassword = confirmPasswordEditText.text.toString().trim()

            if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (newPassword != confirmPassword) {
                Toast.makeText(this, "As senhas não coincidem", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val client = OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.15.61/") // Substitua pelo IP local da máquina
                .addConverterFactory(GsonConverterFactory.create())
                .build()


            val apiService = retrofit.create(ApiService::class.java)
            val call = apiService.updatePassword(email, newPassword)

            // Debug: log para verificar os dados enviados
            println("Email: $email")
            println("Nova Senha: $newPassword")

            call.enqueue(object : Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    if (response.isSuccessful) {
                        val apiResponse = response.body()
                        if (apiResponse?.status == "success") {
                            Toast.makeText(this@UpdatePasswordActivity, apiResponse.message, Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            Toast.makeText(this@UpdatePasswordActivity, apiResponse?.message ?: "Erro desconhecido", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@UpdatePasswordActivity, "Erro ao atualizar senha", Toast.LENGTH_SHORT).show()
                        println("Erro HTTP: ${response.code()} -> ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    Toast.makeText(this@UpdatePasswordActivity, "Erro na conexão: ${t.message}", Toast.LENGTH_SHORT).show()
                    println("Erro de conexão: ${t.localizedMessage}")
                }
            })
        }

        // Botão para cancelar e voltar para a tela de login
        cancelButton.setOnClickListener {
            finish() // Encerra esta tela e volta à anterior
        }

    }
}
