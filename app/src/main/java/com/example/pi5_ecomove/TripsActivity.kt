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
        val viagens = listOf(
            Viagem("João", "R$25,00", 4.93f),
            Viagem("Maria", "R$30,00", 4.85f),
            Viagem("Pedro", "R$20,00", 4.97f),
            Viagem("Ana", "R$18,00", 4.92f)
        )

        val adapter = ViagemAdapter(viagens)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewViagens)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        }
    }

