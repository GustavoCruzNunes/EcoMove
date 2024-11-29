package com.example.pi5_ecomove

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TripAdapter(private val tripList: List<TripModel>) : RecyclerView.Adapter<TripAdapter.TripViewHolder>() {

    class TripViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val driverName: TextView = itemView.findViewById(R.id.driverName)
        val driverRating: TextView = itemView.findViewById(R.id.driverRating)
        val tripPrice: TextView = itemView.findViewById(R.id.tripPrice)
        val driverImage: ImageView = itemView.findViewById(R.id.driverImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_trip, parent, false)
        return TripViewHolder(view)
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        val trip = tripList[position]
        holder.driverName.text = trip.driverName
        holder.driverRating.text = trip.driverRating.toString() // Converte Double para String
        holder.tripPrice.text = "R$ ${trip.price}" // Formata o preço como moeda
        // Configurar imagem do motorista, se houver lógica para carregar imagens
    }

    override fun getItemCount(): Int {
        return tripList.size
    }
}
