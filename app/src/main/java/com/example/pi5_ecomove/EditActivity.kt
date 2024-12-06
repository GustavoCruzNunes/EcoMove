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

class EditActivity : AppCompatActivity() {

    private lateinit var apiService: ApiService
    private val TAG = "EditActivity" // Tag para os logs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val editOriginField = findViewById<EditText>(R.id.editOriginField)
        val editDestinationField = findViewById<EditText>(R.id.editDestinationField)
        val saveChangesButton = findViewById<Button>(R.id.saveChangesButton)
        val cancelTripButton = findViewById<Button>(R.id.cancelTripButton)

        // Configuração do Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.15.61/") // Substitua pelo IP ou URL do backend
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiService = retrofit.create(ApiService::class.java)

        val id = getSharedPreferences("my_prefs", MODE_PRIVATE).getInt("idLogin", 0) // ID da viagem
        if (id != -1) {
            Log.d(TAG, "Carregando detalhes da viagem com id: $id")
            // Carregar os detalhes da viagem para preencher os camposs
            loadTripDetails(id, editOriginField, editDestinationField)
        } else {
            Log.e(TAG, "Erro ao carregar a viagem: id inválido.")
            Toast.makeText(this, "Erro ao carregar a viagem.", Toast.LENGTH_SHORT).show()
            finish()
        }

        saveChangesButton.setOnClickListener {
            val origin = editOriginField.text.toString()
            val destination = editDestinationField.text.toString()

            if (origin.isNotEmpty() && destination.isNotEmpty()) {
                Log.d(TAG, "Enviando dados para salvar: id=$id, origem=$origin, destino=$destination")
                // Salvar alterações de origem e destino no backend
                saveTripDetails(id, origin, destination)
            } else {
                Log.w(TAG, "Campos de origem ou destino estão vazios.")
                Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            }
        }
        cancelTripButton.setOnClickListener {
            deleteTrip(id)
        }

    }

    private fun deleteTrip(id: Int) {
        val call = apiService.deleteTrip(id)
        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        if (it.success) {
                            Log.d("DeleteTrip", "Viagem deletada com sucesso.")
                            Toast.makeText(this@EditActivity, "Viagem deletada com sucesso!", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@EditActivity,TripsActivity::class.java)
                            intent.putExtra("Atualizar_lista", true)
                            startActivity(intent)
                        } else {
                            Log.e("DeleteTrip", "Erro ao deletar: ${it.message}")
                            Toast.makeText(this@EditActivity, "Erro ao deletar: ${it.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Log.e("DeleteTrip", "Erro no servidor: ${response.errorBody()?.string()}")
                    Toast.makeText(this@EditActivity, "Erro no servidor: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.e("DeleteTrip", "Erro de conexão: ${t.message}")
                Toast.makeText(this@EditActivity, "Erro de conexão: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun loadTripDetails(id: Int, originField: EditText, destinationField: EditText) {
        apiService.getTripDetails(id).enqueue(object : Callback<TripModel> {
            override fun onResponse(call: Call<TripModel>, response: Response<TripModel>) {
                if (response.isSuccessful) {
                    val trip = response.body()
                    trip?.let {
                        Log.d(TAG, "Detalhes da viagem carregados com sucesso: $it")
                        originField.setText(it.endereco_origem)
                        destinationField.setText(it.endereco_destino)
                    } ?: run {
                        Log.e(TAG, "Dados da viagem não encontrados para id: $id")
                        Toast.makeText(this@EditActivity, "Dados da viagem não encontrados.", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                } else {
                    Log.e(TAG, "Erro ao carregar detalhes: ${response.errorBody()?.string()}")
                    Toast.makeText(this@EditActivity, "Erro ao carregar detalhes.", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }

            override fun onFailure(call: Call<TripModel>, t: Throwable) {
                Log.e(TAG, "Erro de conexão ao carregar detalhes: ${t.message}", t)
                Toast.makeText(this@EditActivity, "Erro na conexão: ${t.message}", Toast.LENGTH_SHORT).show()
                finish()
            }
        })
    }

    private fun saveTripDetails(id: Int, origin: String, destination: String) {
        Log.e(TAG, "Erro de conexão ao carregar detalhes: ${id} ${origin} ${destination}")
        val call = apiService.updateTrip(
            id = id,
            enderecoOrigem = origin,
            enderecoDestino = destination,
        )

        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    if (apiResponse?.success == true) {
                        Log.d(TAG, "Alterações salvas com sucesso: ${apiResponse.message}")
                        Toast.makeText(this@EditActivity, "Alterações salvas com sucesso!", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this@EditActivity,TripsActivity::class.java)
                        intent.putExtra("Atualizar_lista", true)
                        startActivity(intent)

                    } else {
                        Log.e(TAG, "Erro ao salvar alterações: ${apiResponse?.message}")
                        Toast.makeText(this@EditActivity, "Erro ao salvar alterações: ${apiResponse?.message}", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e(TAG, "Erro na resposta do servidor: ${response.errorBody()?.string()}")
                    Toast.makeText(this@EditActivity, "Erro na resposta: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.e(TAG, "Erro de conexão ao salvar alterações: ${t.message}", t)
                Toast.makeText(this@EditActivity, "Erro de conexão: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}