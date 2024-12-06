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
import java.util.*

class OfferActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offer)

        // Referências aos elementos do layout
        val boardingEditText: EditText = findViewById(R.id.boardingAddressEditText)
        val desembarqueEditText = findViewById<EditText>(R.id.Desembarque)
        val dayEditText: EditText = findViewById(R.id.dayEditText)
        val hourEditText: EditText = findViewById(R.id.hourEditText)
        val minuteEditText: EditText = findViewById(R.id.minuteEditText)
        val priceEditText: EditText = findViewById(R.id.priceEditText)
        val confirmButton: Button = findViewById(R.id.confirmButton)
        val cancelButton: Button = findViewById(R.id.cancelButton)

        // Listener do botão "Confirmar"
        confirmButton.setOnClickListener {
            val boarding = boardingEditText.text.toString()
            val desembarque = desembarqueEditText.text.toString()
            val day = dayEditText.text.toString()
            val hour = hourEditText.text.toString()
            val minute = minuteEditText.text.toString()
            val price = priceEditText.text.toString()

            if (boarding.isEmpty() || desembarque.isEmpty() || day.isEmpty() || hour.isEmpty() || minute.isEmpty() || price.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            } else {
                val calendar = Calendar.getInstance()
                val currentYear = calendar.get(Calendar.YEAR)
                val currentMonth = calendar.get(Calendar.MONTH) + 1 // Meses começam em 0
                val dateTime = "$currentYear-$currentMonth-$day $hour:$minute:00"
                val userId = 1 // Substitua pelo ID real do usuário
                val acceptPet = 0 // Ajuste conforme necessário
                val seats = 1 // Substitua pelo número real de lugares

                // Configurando Retrofit
                val retrofit = Retrofit.Builder()
                    .baseUrl("http://192.168.10.26") // Atualize conforme necessário
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val apiService = retrofit.create(ApiService::class.java)

                val tipo = "oferecida"
                val call = apiService.offerTrip(
                    tipo,
                    userId.toString(), // Certifique-se de enviar como String, pois o método exige String
                    boarding,
                    desembarque,
                    dateTime,
                    seats,
                    acceptPet.toDouble(),
                    price.toDouble()
                )

                call.enqueue(object : Callback<ApiResponse> {
                    override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@OfferActivity, "Corrida oferecida com sucesso!", Toast.LENGTH_SHORT).show()

                            // Redirecionar para a HomeActivity após sucesso
                            val intent = Intent(this@OfferActivity, HomeActivity::class.java)
                            startActivity(intent)
                            finish() // Finaliza a OfferActivity
                        } else {
                            Toast.makeText(this@OfferActivity, "Erro: ${response.errorBody()?.string()}", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                        Toast.makeText(this@OfferActivity, "Erro na conexão: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }

        // Listener do botão "Cancelar"
        cancelButton.setOnClickListener {
            // Redirecionar para a HomeActivity ao cancelar
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish() // Finaliza a OfferActivity
        }
    }
}
