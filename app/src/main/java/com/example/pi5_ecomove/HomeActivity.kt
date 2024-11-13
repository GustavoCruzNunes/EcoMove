package com.example.pi5_ecomove

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Referência ao botão "Oferecer"
        val offerButton = findViewById<Button>(R.id.offerButton)

        // Configurar a ação do botão para abrir a OfferActivity
        offerButton.setOnClickListener {
            val intent = Intent(this, OfferActivity::class.java)
            startActivity(intent)
        }

        // Referência ao botão "Solicitar"
        val requestButton = findViewById<Button>(R.id.requestButton)

        // Configurar a ação do botão para abrir a RequestActivity
        requestButton.setOnClickListener {
            val intent = Intent(this, RequestActivity::class.java)
            startActivity(intent)
        }
    }
}
