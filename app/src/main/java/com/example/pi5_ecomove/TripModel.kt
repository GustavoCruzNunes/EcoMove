package com.example.pi5_ecomove

data class TripModel(
    val id: Int,
    val driverName: String,
    val driverRating: Double,
    val price: Double, // Corrigido para Double
    val origin: String,
    val destination: String,
    val date: String
)
