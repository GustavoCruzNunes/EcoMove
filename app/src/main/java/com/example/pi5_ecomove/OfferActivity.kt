package com.example.pi5_ecomove

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class OfferActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offer)

        // Referenciar os elementos da interface
        val origemEditText = findViewById<EditText>(R.id.addressEditText)
        val dataPartidaEditText = findViewById<EditText>(R.id.dayEditText)
        val horaPartidaEditText = findViewById<EditText>(R.id.hourEditText)
        val minutoPartidaEditText = findViewById<EditText>(R.id.minuteEditText)
        val dataChegadaEditText = findViewById<EditText>(R.id.arrivalDayEditText)
        val horaChegadaEditText = findViewById<EditText>(R.id.arrivalHourEditText)
        val minutoChegadaEditText = findViewById<EditText>(R.id.arrivalMinuteEditText)
        val precoEditText = findViewById<EditText>(R.id.priceEditText)
        val confirmButton = findViewById<Button>(R.id.confirmButton)
        val cancelButton = findViewById<Button>(R.id.cancelButton)

        // Ação do botão Confirmar
        confirmButton.setOnClickListener {
            val tipo = "oferecer"
            val origem = origemEditText.text.toString()
            val dataPartida = "${dataPartidaEditText.text} ${horaPartidaEditText.text}:${minutoPartidaEditText.text}"
            val dataChegada = "${dataChegadaEditText.text} ${horaChegadaEditText.text}:${minutoChegadaEditText.text}"
            val preco = precoEditText.text.toString().toDoubleOrNull() ?: 0.0

            if (origem.isEmpty() || dataPartida.isEmpty() || dataChegada.isEmpty() || preco <= 0) {
                Toast.makeText(this, "Por favor, preencha todos os campos corretamente!", Toast.LENGTH_SHORT).show()
            } else {
                enviarOferta(tipo, origem, dataPartida, dataChegada, preco)
            }
        }

        // Ação do botão Cancelar
        cancelButton.setOnClickListener {
            finish() // Voltar para a tela anterior
        }

    }

    private fun enviarOferta(tipo: String, origem: String, dataPartida: String, dataChegada: String, preco: Double) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.15.61") // Substitua pela URL do backend
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        val call = apiService.criarViagem(tipo, origem, dataPartida, dataChegada, preco)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@OfferActivity, "Viagem cadastrada com sucesso!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@OfferActivity, "Erro ao cadastrar a viagem.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@OfferActivity, "Erro de conexão: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
