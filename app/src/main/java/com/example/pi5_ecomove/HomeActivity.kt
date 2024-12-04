package com.example.pi5_ecomove

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class HomeActivity : AppCompatActivity(), OnMapReadyCallback  {

    private lateinit var googleMap: GoogleMap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Referência ao botão "Oferecer"
        val offerButton = findViewById<Button>(R.id.offerButton)

        // Configurar a ação do botão para abrir a OfferActivity
        offerButton.setOnClickListener {
            val intent = Intent(this, OfferActivity::class.java)
            startActivity(intent)
        }

        // Referência ao botão "Solicitar"
        val requestButton = findViewById<Button>(R.id.requestButton)

        // Configurar a ação do botão para abrir a RequestActivity
        requestButton.setOnClickListener {
            val intent = Intent(this, RequestActivity::class.java)
            startActivity(intent)

        }
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }
    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        // Configurações do mapa (opcional)
        googleMap.uiSettings.isZoomControlsEnabled = true
        googleMap.uiSettings.isMyLocationButtonEnabled = true

        // Define uma posição inicial no mapa
        val defaultLocation = LatLng(-23.55052, -46.633308) // São Paulo
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 12f))

        // Adiciona um marcador no local padrão
        googleMap.addMarker(
            MarkerOptions()
                .position(defaultLocation)
                .title("Local padrão")
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Gerencia os cliques nos itens do menu
        return when (item.itemId) {

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
            R.id.action_item_trip -> {
                Log.d("Menu", "Historico de corrida criado")
                // Navega para a tela de historico de corrida
                val intent = Intent(this, TripsActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
