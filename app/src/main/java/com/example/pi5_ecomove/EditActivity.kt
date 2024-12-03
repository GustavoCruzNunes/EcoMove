package com.example.pi5_ecomove

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class EditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val editAddressField = findViewById<EditText>(R.id.editAddressField)
        val editDateField = findViewById<EditText>(R.id.editDateField)
        val editTimeField = findViewById<EditText>(R.id.editTimeField)
        val saveChangesButton = findViewById<Button>(R.id.saveChangesButton)
        val cancelTripButton = findViewById<Button>(R.id.cancelTripButton)

        // Salvando alterações
        saveChangesButton.setOnClickListener {
            val address = editAddressField.text.toString()
            val date = editDateField.text.toString()
            val time = editTimeField.text.toString()

            if (address.isNotEmpty() && date.isNotEmpty() && time.isNotEmpty()) {
                // Salvar alterações no banco ou enviar para o backend
                Toast.makeText(this, "Alterações salvas!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            }
        }

        // Cancelando corrida
        cancelTripButton.setOnClickListener {
            Toast.makeText(this, "Corrida cancelada!", Toast.LENGTH_SHORT).show()
            finish() // Voltar para a tela anterior
        }
    }
}
