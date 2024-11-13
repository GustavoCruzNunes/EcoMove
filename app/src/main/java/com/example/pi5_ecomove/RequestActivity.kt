package com.example.pi5_ecomove

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.Toast

class RequestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request)

        val searchButton = findViewById<Button>(R.id.searchButton)
        val cancelButton = findViewById<Button>(R.id.cancelButton)

        searchButton.setOnClickListener {
            Toast.makeText(this, "Buscar serviço", Toast.LENGTH_SHORT).show()
        }

        cancelButton.setOnClickListener {
            finish() // Encerra a Activity e volta para a anterior
        }
    }
}
