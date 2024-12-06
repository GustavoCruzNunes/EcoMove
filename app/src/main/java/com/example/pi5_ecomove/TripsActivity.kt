package com.example.pi5_ecomove

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TripsActivity : BaseActivity() {

    private lateinit var tripAdapter: TripAdapter
    private lateinit var tripsRecyclerView: RecyclerView
    private val tripList = mutableListOf<TripModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trips)
        if (intent.hasExtra("Atualizar_lista")) {
            tripAdapter.notifyDataSetChanged()

        }

        tripsRecyclerView = findViewById(R.id.tripsRecyclerView)
        tripsRecyclerView.layoutManager = LinearLayoutManager(this)

        // Inicialize o adapter com a ação de clique no lápis
        tripAdapter = TripAdapter(this, tripList) { tripId ->
            navigateToEditActivity(tripId)
        }
        tripsRecyclerView.adapter = tripAdapter

        // Carregar os dados de viagens (do banco de dados ou API)
        loadTripsData()
    }

    private fun loadTripsData() {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.15.61/") // Certifique-se de que o IP está correto
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        val call = apiService.getTrips()

        call.enqueue(object : Callback<List<TripModel>> {
            override fun onResponse(
                call: Call<List<TripModel>>,
                response: Response<List<TripModel>>
            ) {
                if (response.isSuccessful) {
                    val trips = response.body()
                    if (trips != null) {
                        tripList.clear()
                        Log.d(TAG, "Enviando dados para salvar: id=$tripList")
                        tripList.addAll(trips.sortedByDescending { it.id })
                        tripAdapter.notifyDataSetChanged()
                    } else {
                        Toast.makeText(
                            this@TripsActivity,
                            "Nenhuma viagem encontrada",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@TripsActivity,
                        "Erro ao carregar viagens",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<TripModel>>, t: Throwable) {
                Toast.makeText(
                    this@TripsActivity,
                    "Erro na conexão: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun navigateToEditActivity(tripId: Int) {
        val intent = Intent(this, EditActivity::class.java)
        intent.putExtra("tripId", tripId)
        startActivity(intent)
    }

}