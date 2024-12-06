package com.example.pi5_ecomove

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime

class RequestActivity : BaseActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request)

        // Referenciar os componentes do layout
        val boardingEditText = findViewById<EditText>(R.id.boardingAddressEditText)
        val desembarqueEditText = findViewById<EditText>(R.id.Desembarque)
        val dayEditText = findViewById<EditText>(R.id.dayEditText)
        val hourEditText = findViewById<EditText>(R.id.hourEditText)
        val minuteEditText = findViewById<EditText>(R.id.minuteEditText)
        val priceEditText = findViewById<EditText>(R.id.priceEditText)
        val searchButton = findViewById<Button>(R.id.searchButton)
        val cancelButton = findViewById<Button>(R.id.cancelButton)

        // Listener do botão Buscar (Confirmar)
        searchButton.setOnClickListener {
            val boarding = boardingEditText.text.toString()
            val desembarque = desembarqueEditText.text.toString()
            val day = dayEditText.text.toString()
            val hour = hourEditText.text.toString()
            val minute = minuteEditText.text.toString()
            val price = priceEditText.text.toString()

            if (boarding.isEmpty() || desembarque.isEmpty() || day.isEmpty() || hour.isEmpty() || minute.isEmpty() || price.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            } else {
                val dateTime = "${LocalDateTime.now().year}-${LocalDateTime.now().monthValue}-$day $hour:$minute:00" // Combina data e hora
                val userId = 1 // Substitua pelo ID real do usuário
                val seats = 1 // Substitua pelo número real de lugares
                val acceptPet = 0 // Ajuste conforme necessário

                val gson = GsonBuilder()
                    .setLenient()
                    .create()

                val retrofit = Retrofit.Builder()
                    .baseUrl("http://192.168.15.61/") // Atualize conforme necessário
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val apiService = retrofit.create(ApiService::class.java)

                val tipo = "solicitada"
                val call = apiService.requestTrip(
                    userId,
                    boarding,
                    desembarque,
                    dateTime,
                    seats,
                    acceptPet,
                    price.toDouble()
                )

                call.enqueue(object : Callback<ApiResponse> {
                    override fun onResponse(call: Call<ApiResponse>, response: retrofit2.Response<ApiResponse>) {
                        if (response.isSuccessful) {
                            val apiResponse = response.body()
                            Toast.makeText(this@RequestActivity, apiResponse?.message, Toast.LENGTH_SHORT).show()

                            // Redirecionar para a HomeActivity após sucesso
                            val intent = Intent(this@RequestActivity, HomeActivity::class.java)
                            startActivity(intent)
                            finish() // Finaliza a RequestActivity
                        } else {
                            Toast.makeText(this@RequestActivity, "Erro ao solicitar a viagem", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                        Toast.makeText(this@RequestActivity, "Erro: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }

        // Listener do botão Cancelar
        cancelButton.setOnClickListener {
            // Voltar para a HomeActivity ao cancelar
            val intent = Intent(this@RequestActivity, HomeActivity::class.java)
            startActivity(intent)
            finish() // Finaliza a RequestActivity
        }
    }
}