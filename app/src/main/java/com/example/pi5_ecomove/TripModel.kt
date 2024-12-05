package com.example.pi5_ecomove
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "viagens")
data class TripModel(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val tipo: String,
    val usuarioId: Int,
    val endereco_origem: String,
    val endereco_destino: String,
    val data_horario_partida: String,
    val lugares: Int,
    val aceitaPet: Boolean,
    val preco: Double,
    val dataCriacao: String
)


