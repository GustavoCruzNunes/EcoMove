package com.example.pi5_ecomove

data class UserResponse(
    val success: Boolean,
    val data: UserData?
)

data class UserData(
    val nome: String,
    val email: String,
    val data_criacao: String
)