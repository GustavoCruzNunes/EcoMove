package com.example.pi5_ecomove

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserActivity : AppCompatActivity() {
    private val nameLabel by lazy { findViewById<TextView>(R.id.nameLabel) }
    private val emailLabel by lazy { findViewById<TextView>(R.id.emailLabel) }
    private val creationDateLabel by lazy { findViewById<TextView>(R.id.creationDateLabel) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        val id = getSharedPreferences("my_prefs", MODE_PRIVATE).getInt("idLogin", 0)
        getUserInfo(id)

        // Botões
        val changePasswordButton: Button = findViewById(R.id.changePasswordButton)
        val homeButton: Button = findViewById(R.id.homeButton)
        val historyButton: Button = findViewById(R.id.historyButton)
        val aboutUsButton: Button = findViewById(R.id.aboutUsButton)

        // Navegação
        changePasswordButton.setOnClickListener {
            // Ir para a tela de Alterar Senha
            val intent = Intent(this, ForgotPassword::class.java)
            startActivity(intent)
        }

        homeButton.setOnClickListener {
            // Ir para a tela Home
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        historyButton.setOnClickListener {
            // Ir para a tela Histórico
            val intent = Intent(this, TripsActivity::class.java)
            startActivity(intent)
        }

        aboutUsButton.setOnClickListener {
            // Ir para a tela Quem Somos Nós
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getUserInfo(userId: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.15.162/") // Certifique-se de que o IP está correto
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        if (userId != 0) {
            val apiService = retrofit.create(ApiService::class.java)
            val call = apiService.getUserInfo(userId)

            call.enqueue(object : Callback<UserData> {
                override fun onResponse(p0: Call<UserData>, p1: Response<UserData>) {
                    if (p1.isSuccessful) {
                        Log.i("Login 3", p1.body().toString())
                        nameLabel.text = p1.body()?.nome
                        emailLabel.text = p1.body()?.email
                        creationDateLabel.text = p1.body()?.data_criacao
                    }
                }

                override fun onFailure(p0: Call<UserData>, p1: Throwable) {
                    Log.e("Login 4", "ERRO $p1")
                }
            })
        }
    }
}