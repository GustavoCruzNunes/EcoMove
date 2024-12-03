package com.example.pi5_ecomove

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
        }

        override fun onCreateOptionsMenu(menu: Menu?): Boolean {
                // Infla o menu_main.xml para todas as telas que herdam desta BaseActivity
                menuInflater.inflate(R.menu.menu_main, menu)
                return true
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
                // Gerencia os cliques nos itens do menu
                return when (item.itemId) {
                        R.id.action_settings -> {
                                Log.d("Menu", "Configurações clicadas")
                                // Adicione a lógica para Configurações
                                true
                        }
                        R.id.action_about -> {
                                Log.d("Menu", "Sobre clicado")
                                // Navega para a AboutActivity
                                val intent = Intent(this, AboutActivity::class.java)
                                startActivity(intent)
                                true
                        }
                        R.id.action_who_we_are -> {
                                Log.d("Menu", "Quem Somos clicado")
                                // Navega para a tela "Quem Somos Nós"
                                val intent = Intent(this, AboutActivity::class.java)
                                startActivity(intent)
                                true
                        }
                        R.id.action_home -> {
                                Log.d("Menu", "Menu Principal clicado")
                                // Navega para a HomeActivity
                                val intent = Intent(this, HomeActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(intent)
                                true
                        }
                        else -> super.onOptionsItemSelected(item)
                }
        }
}
