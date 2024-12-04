package com.example.pi5_ecomove

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

class UpdatePassword : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_password)

        val newPasswordEditText = findViewById<EditText>(R.id.newPasswordEditText)
        val confirmPasswordEditText = findViewById<EditText>(R.id.confirmPasswordEditText)
        val updatePasswordButton = findViewById<Button>(R.id.updatePasswordButton)

        // Configurando Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.15.61/") // Substitua pelo URL do backend
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        updatePasswordButton.setOnClickListener {
            val newPassword = newPasswordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            } else if (newPassword != confirmPassword) {
                Toast.makeText(this, "As senhas não coincidem", Toast.LENGTH_SHORT).show()
            } else {
                val email = intent.getStringExtra("email") ?: "" // Email enviado pela tela anterior

                val call = apiService.UpdatePassword(email, newPassword)
                call.enqueue(object : Callback<ApiResponse> {
                    override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@UpdatePassword, "Senha atualizada com sucesso!", Toast.LENGTH_SHORT).show()
                            finish() // Fecha a Activity após sucesso
                        } else {
                            Toast.makeText(this@UpdatePassword, "Erro ao atualizar senha", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                        Toast.makeText(this@UpdatePassword, "Erro: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }
}
