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

class EditActivity : AppCompatActivity() {

    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        // Referências aos campos
        val editOriginField = findViewById<EditText>(R.id.editOriginField)
        val editDestinationField = findViewById<EditText>(R.id.editDestinationField)
        val editDateField = findViewById<EditText>(R.id.editDateField)
        val editTimeField = findViewById<EditText>(R.id.editTimeField)
        val saveChangesButton = findViewById<Button>(R.id.saveChangesButton)
        val cancelTripButton = findViewById<Button>(R.id.cancelTripButton)

        // Inicializando Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.15.61/") // Substitua pelo IP ou URL do seu backend
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiService = retrofit.create(ApiService::class.java)

        // Pegando dados enviados
        val tripId = intent.getIntExtra("tripId", -1) // ID da corrida
        if (tripId != -1) {
            // Carregar os dados da corrida do backend
            loadTripDetails(tripId, editOriginField, editDestinationField, editDateField, editTimeField)
        }

        // Salvando alterações
        saveChangesButton.setOnClickListener {
            val origin = editOriginField.text.toString()
            val destination = editDestinationField.text.toString()
            val date = editDateField.text.toString()
            val time = editTimeField.text.toString()

            if (origin.isNotEmpty() && destination.isNotEmpty() && date.isNotEmpty() && time.isNotEmpty()) {
                // Salvar alterações no backend
                saveTripDetails(tripId, origin, destination, "$date $time")
            } else {
                Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadTripDetails(
        tripId: Int,
        originField: EditText,
        destinationField: EditText,
        dateField: EditText,
        timeField: EditText
    ) {
        apiService.getTripDetails(tripId).enqueue(object : Callback<TripModel> {
            override fun onResponse(call: Call<TripModel>, response: Response<TripModel>) {
                if (response.isSuccessful) {
                    val trip = response.body()
                    trip?.let {
                        originField.setText(it.endereco_origem)
                        destinationField.setText(it.endereco_destino)
                        val dateTime = it.data_horario_partida.split(" ")
                        dateField.setText(dateTime[0])
                        timeField.setText(dateTime[1])
                    }
                } else {
                    Toast.makeText(this@EditActivity, "Erro ao carregar detalhes", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<TripModel>, t: Throwable) {
                Toast.makeText(this@EditActivity, "Erro na conexão: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun saveTripDetails(tripId: Int, origin: String, destination: String, dateTime: String) {
        apiService.updateTrip(tripId, origin, destination, dateTime).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@EditActivity, "Alterações salvas com sucesso!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@EditActivity, "Erro ao salvar alterações", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Toast.makeText(this@EditActivity, "Erro na conexão: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
