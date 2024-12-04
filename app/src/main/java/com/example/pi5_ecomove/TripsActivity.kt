package com.example.pi5_ecomove

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TripsActivity : AppCompatActivity() {

    private lateinit var tripAdapter: TripAdapter
    private lateinit var tripsRecyclerView: RecyclerView
    private val tripList = mutableListOf<TripModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trips)

        tripsRecyclerView = findViewById(R.id.tripsRecyclerView)
        tripsRecyclerView.layoutManager = LinearLayoutManager(this)

        // Inicialize o adapter antes de usar
        tripAdapter = TripAdapter(this, tripList)
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
            override fun onResponse(call: Call<List<TripModel>>, response: Response<List<TripModel>>) {
                if (response.isSuccessful) {
                    val trips = response.body()
                    if (trips != null) {
                        tripList.clear()
                        tripList.addAll(trips)
                        tripAdapter.notifyDataSetChanged()
                    } else {
                        Toast.makeText(this@TripsActivity, "Nenhuma viagem encontrada", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@TripsActivity, "Erro ao carregar viagens", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<TripModel>>, t: Throwable) {
                Toast.makeText(this@TripsActivity, "Erro na conexão: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Gerencia os cliques nos itens do menu
        return when (item.itemId) {

            R.id.action_who_we_are -> {
                Log.d("Menu", "Quem Somos clicado")
                // Navega para a tela "Quem Somos Nós"
                val intent = Intent(this, AboutActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_home -> {
                Log.d("Menu", "Menu Principal clicado")
                // Navega para a HomeActivity
                val intent = Intent(this, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                true
            }
            R.id.action_item_trip -> {
                Log.d("Menu", "Historico de corrida criado")
                // Navega para a tela de historico de corrida
                val intent = Intent(this, TripsActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

