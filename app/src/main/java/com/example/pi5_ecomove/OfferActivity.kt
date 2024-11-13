package com.example.pi5_ecomove

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.Toast

class OfferActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offer)

        val confirmButton = findViewById<Button>(R.id.confirmButton)
        val cancelButton = findViewById<Button>(R.id.cancelButton)

        confirmButton.setOnClickListener {
            Toast.makeText(this, "Oferta confirmada", Toast.LENGTH_SHORT).show()
        }

        cancelButton.setOnClickListener {
            finish() // Encerra a Activity e volta para a anterior
        }
    }
}
