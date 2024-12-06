package com.example.pi5_ecomove

import AppDatabase
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room

class MainActivity : AppCompatActivity() {

    private val database: AppDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "ecomove_database"
        ).build()
    }

    private val tripDao: TripDao by lazy {
        database.tripDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referência ao botão "Acessar"
        val accessButton = findViewById<Button>(R.id.accessButton)

        // Configurar a ação do botão para abrir a LoginActivity
        accessButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}