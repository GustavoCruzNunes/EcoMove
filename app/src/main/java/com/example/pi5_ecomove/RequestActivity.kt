package com.example.pi5_ecomove

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RequestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request)

        // Referenciar os componentes do layout
        val boardingEditText = findViewById<EditText>(R.id.boardingAddressEditText)
        val desembarqueEditText = findViewById<EditText>(R.id.Desembarque)
        val dayEditText = findViewById<EditText>(R.id.dayEditText)
        val hourEditText = findViewById<EditText>(R.id.hourEditText)
        val minuteEditText = findViewById<EditText>(R.id.minuteEditText)
        val priceEditText = findViewById<EditText>(R.id.priceEditText)

        val searchButton = findViewById<Button>(R.id.searchButton)
        val cancelButton = findViewById<Button>(R.id.cancelButton)

        // Listener do botão Buscar (Confirmar)
        searchButton.setOnClickListener {
            // Capturar os valores inseridos nos campos de texto
            val boarding = boardingEditText.text.toString()
            val desembarque = desembarqueEditText.text.toString()
            val day = dayEditText.text.toString()
            val hour = hourEditText.text.toString()
            val minute = minuteEditText.text.toString()
            val price = priceEditText.text.toString()

            // Validar os campos
            if (boarding.isEmpty() || desembarque.isEmpty() || day.isEmpty() || hour.isEmpty() || minute.isEmpty() || price.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Dados confirmados. Serviço solicitado.", Toast.LENGTH_SHORT).show()
                // Aqui você pode enviar os dados para um servidor ou outra lógica
            }
        }

        // Listener do botão Cancelar
        cancelButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java) // Navegar para HomeActivity
            startActivity(intent)
            finish() // Finaliza a RequestActivity
        }
    }
}
