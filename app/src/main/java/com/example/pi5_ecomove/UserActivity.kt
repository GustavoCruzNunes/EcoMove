package com.example.pi5_ecomove

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class UserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        // Botões
        val changePasswordButton: Button = findViewById(R.id.changePasswordButton)
        val homeButton: Button = findViewById(R.id.homeButton)
        val historyButton: Button = findViewById(R.id.historyButton)
        val aboutUsButton: Button = findViewById(R.id.aboutUsButton)

        // Navegação
        changePasswordButton.setOnClickListener {
            // Ir para a tela de Alterar Senha
            val intent = Intent(this, ForgotPassword::class.java)
            startActivity(intent)
        }

        homeButton.setOnClickListener {
            // Ir para a tela Home
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        historyButton.setOnClickListener {
            // Ir para a tela Histórico
            val intent = Intent(this, TripsActivity::class.java)
            startActivity(intent)
        }

        aboutUsButton.setOnClickListener {
            // Ir para a tela Quem Somos Nós
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }
    }
}
