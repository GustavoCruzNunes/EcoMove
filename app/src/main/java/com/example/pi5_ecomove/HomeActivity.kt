package com.example.pi5_ecomove

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class HomeActivity : BaseActivity(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val nomeCompleto = intent.getStringExtra("nome_completo") // Recebe o nome completo
        val userNameTextView = findViewById<TextView>(R.id.userNameTextView)
        val profileImageView = findViewById<ImageView>(R.id.profileImageView)

        // Atualiza a TextView com o nome completo
        userNameTextView.text = nomeCompleto

        profileImageView.setOnClickListener {
            val intent = Intent(this, UserActivity::class.java) // Substitua pela sua activity de destino
            startActivity(intent)
        }

        // Referência ao botão "Oferecer"
        val offerButton = findViewById<Button>(R.id.offerButton)
        offerButton.setOnClickListener {
            val intent = Intent(this, OfferActivity::class.java)
            startActivity(intent)
        }

        // Referência ao botão "Solicitar"
        val requestButton = findViewById<Button>(R.id.requestButton)
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
        val defaultLocation = LatLng(-23.6699, -46.7012) // São Paulo
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 12f))

        // Adiciona um marcador no local padrão
        googleMap.addMarker(
            MarkerOptions()
                .position(defaultLocation)
                .title("Local padrão")
        )
    }

}

