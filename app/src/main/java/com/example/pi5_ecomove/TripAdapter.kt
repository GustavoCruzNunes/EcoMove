package com.example.pi5_ecomove

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TripAdapter(private val context: Context, private val tripList: List<TripModel>) :
    RecyclerView.Adapter<TripAdapter.TripViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_trip, parent, false)
        return TripViewHolder(view)
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        val trip = tripList[position]
        holder.bind(trip)

    }

    override fun getItemCount(): Int {
        return tripList.size
    }

    inner class TripViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val origemTextView: TextView = itemView.findViewById(R.id.origTextView)
        private val destinoTextView: TextView = itemView.findViewById(R.id.destinoTextView)
        private val dataPartidaTextView: TextView = itemView.findViewById(R.id.dataPartidaTextView)
        private val precoTextView: TextView = itemView.findViewById(R.id.precoTextView)
        val editButton: ImageButton = itemView.findViewById(R.id.editButton)

        fun bind(trip: TripModel) {
            origemTextView.text = "Origem: ${trip.endereco_origem}"
            destinoTextView.text = "Destino: ${trip.endereco_destino}"
            dataPartidaTextView.text = "Data: ${trip.data_horario_partida}"
            precoTextView.text = "Preço: R$ ${trip.preco}"

            editButton.setOnClickListener {
                val intent = Intent(context, EditActivity::class.java)
                intent.putExtra("tripId", trip.id) // Passar informações da corrida para edição
                context.startActivity(intent)
            }

        }

    }
}
