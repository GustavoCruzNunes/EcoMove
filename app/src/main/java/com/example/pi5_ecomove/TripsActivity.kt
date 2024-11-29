package com.example.pi5_ecomove

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import android.widget.Toast

class TripsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trips)

        // Exemplo de dados para os motoristas
        val tripList = listOf(
            TripModel(1, "Motorista 1", 4.9, 50.0, "Rua A", "Rua B", "2024-12-01"),
            TripModel(2, "Motorista 2", 4.8, 45.0, "Rua C", "Rua D", "2024-12-02"),
            TripModel(3, "Motorista 3", 4.7, 55.0, "Rua E", "Rua F", "2024-12-03"),
            TripModel(4, "Motorista 4", 4.6, 60.0, "Rua G", "Rua H", "2024-12-04"),
            TripModel(5, "Motorista 5", 4.5, 40.0, "Rua I", "Rua J", "2024-12-05")
        )

        // Configurando o RecyclerView
        val recyclerViewTrips = findViewById<RecyclerView>(R.id.recyclerViewTrips)
        recyclerViewTrips.layoutManager = LinearLayoutManager(this)
        recyclerViewTrips.adapter = TripAdapter(tripList)

        // Botão "Solicitar"
        val requestTripButton = findViewById<Button>(R.id.requestTripButton)
        requestTripButton.setOnClickListener {
            Toast.makeText(this, "Solicitar viagem", Toast.LENGTH_SHORT).show()
        }
    }
}
