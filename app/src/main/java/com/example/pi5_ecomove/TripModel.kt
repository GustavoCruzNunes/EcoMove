package com.example.pi5_ecomove
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "viagens")
data class TripModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val tipo: String,
    val usuarioId: Int,
    val enderecoOrigem: String,
    val enderecoDestino: String,
    val dataHorarioPartida: String,
    val lugares: Int,
    val aceitaPet: Boolean,
    val preco: Double,
    val dataCriacao: String
)


