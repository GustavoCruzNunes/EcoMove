package com.example.pi5_ecomove

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import android.widget.Toast

class TripsActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trips)

        // Exemplo de dados para os motoristas
        val tripList = listOf(
            Trip("Nome do Motorista", "★ 4.9", "R$50,00"),
            Trip("Nome do Motorista", "★ 4.8", "R$45,00"),
            Trip("Nome do Motorista", "★ 4.7", "R$55,00"),
            Trip("Nome do Motorista", "★ 4.6", "R$60,00"),
            Trip("Nome do Motorista", "★ 4.5", "R$40,00")
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
