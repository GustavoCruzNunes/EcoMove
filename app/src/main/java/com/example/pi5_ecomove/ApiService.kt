package com.example.pi5_ecomove

import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    // Método para Login
    @GET("login.php")
    fun login(
        @Query("usuario") usuario: String,
        @Query("senha") senha: String
    ): Call<List<LoginResponse>>

    // Método para Registro
    @FormUrlEncoded
    @POST("register.php")
    fun register(
        @Field("nome") nome: String,
        @Field("email") email: String,
        @Field("telefone") telefone: String,
        @Field("senha") senha: String
    ): Call<RegisterResponse>
}

// Classe para tratar a resposta do Login
data class LoginResponse(
    val idlogin: Int,
    val username: String,
    val nome_completo: String
)

// Classe para tratar a resposta do Registro
data class RegisterResponse(
    val status: String,
    val message: String
)
