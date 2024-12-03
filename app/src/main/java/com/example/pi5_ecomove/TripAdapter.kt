package com.example.pi5_ecomove

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class Viagem(
    val motorista: String,
    val preco: String,
    val avaliacao: Float
)

class ViagemAdapter(private val viagens: List<Viagem>) :
    RecyclerView.Adapter<ViagemAdapter.ViagemViewHolder>() {

    class ViagemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val motorista = itemView.findViewById<TextView>(R.id.tvmotorista)
        val preco = itemView.findViewById<TextView>(R.id.tvPreco)
        val avaliacao = itemView.findViewById<TextView>(R.id.tvAvaliacao)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViagemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_trip, parent, false)
        return ViagemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViagemViewHolder, position: Int) {
        val viagem = viagens[position]
        holder.motorista.text = viagem.motorista
        holder.preco.text = viagem.preco
        holder.avaliacao.text = "⭐ ${viagem.avaliacao}"
    }

    override fun getItemCount(): Int = viagens.size
}

