package com.example.pi5_ecomove

import okhttp3.ResponseBody
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

    @FormUrlEncoded
    @POST("viagens.php")
    fun salvarViagem(
        @Field("tipo") tipo: String,
        @Field("usuario_id") usuarioId: Int,
        @Field("endereco_origem") enderecoOrigem: String,
        @Field("endereco_destino") enderecoDestino: String,
        @Field("data_horario_partida") dataHorarioPartida: String,
        @Field("lugares") lugares: Int,
        @Field("aceita_pet") aceitaPet: Boolean,
        @Field("preco") preco: Double
    ): Call<ResponseBody>

    @POST("sua-rota-backend")
    fun criarViagem(
        @Field("tipo") tipo: String,
        @Field("origem") origem: String,
        @Field("dataPartida") dataPartida: String,
        @Field("preco") preco: Double
    ): Call<Void>

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
