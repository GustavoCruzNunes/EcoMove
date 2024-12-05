import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pi5_ecomove.R

class EditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        // Referências aos campos
        val editOriginField = findViewById<EditText>(R.id.editOriginField)
        val editDestinationField = findViewById<EditText>(R.id.editDestinationField)
        val editDateField = findViewById<EditText>(R.id.editDateField)
        val editTimeField = findViewById<EditText>(R.id.editTimeField)
        val saveChangesButton = findViewById<Button>(R.id.saveChangesButton)
        val cancelTripButton = findViewById<Button>(R.id.cancelTripButton)

        // Pegando dados enviados
        val tripId = intent.getIntExtra("tripId", -1) // ID da corrida
        if (tripId != -1) {
            // Carregar os dados da corrida no banco ou lista
            loadTripDetails(tripId, editOriginField, editDestinationField, editDateField, editTimeField)
        }

        // Salvando alterações
        saveChangesButton.setOnClickListener {
            val origin = editOriginField.text.toString()
            val destination = editDestinationField.text.toString()
            val date = editDateField.text.toString()
            val time = editTimeField.text.toString()

            if (origin.isNotEmpty() && destination.isNotEmpty() && date.isNotEmpty() && time.isNotEmpty()) {
                // Salvar alterações no banco ou backend
                saveTripDetails(tripId, origin, destination, date, time)
                Toast.makeText(this, "Alterações salvas!", Toast.LENGTH_SHORT).show()
                finish() // Voltar à tela anterior
            } else {
                Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            }
        }

        // Cancelando a corrida
        cancelTripButton.setOnClickListener {
            cancelTrip(tripId) // Função que cancela a corrida no banco
            Toast.makeText(this, "Corrida cancelada!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun loadTripDetails(
        tripId: Int,
        originField: EditText,
        destinationField: EditText,
        dateField: EditText,
        timeField: EditText
    ) {
        // Aqui você busca os dados no banco de dados ou na lista e preenche os campos
        // Exemplo fictício:
        originField.setText("Endereço de origem carregado")
        destinationField.setText("Endereço de destino carregado")
        dateField.setText("12/12/2024")
        timeField.setText("15:30")
    }

    private fun saveTripDetails(tripId: Int, origin: String, destination: String, date: String, time: String) {
        // Aqui você salva os dados alterados no banco de dados ou backend
        // Exemplo fictício: Atualizar na lista ou banco
    }

    private fun cancelTrip(tripId: Int) {
        // Aqui você cancela a corrida no banco de dados ou lista
    }
}
