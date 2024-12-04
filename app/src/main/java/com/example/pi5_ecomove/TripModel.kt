package com.example.pi5_ecomove

data class TripModel(
    val usuario_id: Int,
    val endereco_origem: String,      // Origem da viagem
    val endereco_destino: String,     // Destino da viagem
    val data_horario_partida: String, // Data da viagem
    val preco: Double,       // Preço da viagem
    val lugares: Int,        // Quantidade de lugares disponíveis
    val aceitaPet: Boolean   // Se aceita animais de estimação
)

