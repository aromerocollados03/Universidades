package com.arc.universidades

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.arc.universidades.databinding.ActivityDatosUniversidadBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class DatosUniversidad : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDatosUniversidadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val universidad = intent.getSerializableExtra("universidad") as? Universidad
        universidad?.let { updateViews(binding, it) }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val universidad = intent.getSerializableExtra("universidad") as? Universidad
        universidad?.let {
            val sydney = LatLng(-34.0, 151.0)
            mMap.addMarker(MarkerOptions().position(sydney).title("Marcador en Sídney"))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15f))
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateViews(binding: ActivityDatosUniversidadBinding, universidad: Universidad) {
        with(binding) {
            tvNombreUniversidad.text = "Nombre: ${universidad.name}"
            tvPais.text = "País: ${universidad.country}"
            tvEstado.text = "Estado: ${universidad.stateProvince ?: "N/A"}"
            tvAlphaCode.text = "Código Alfa: ${universidad.alphaCode}"
            tvWebPages.text = "Web: ${universidad.webPages.joinToString(", ")}"
            tvDominios.text = "Dominio: ${universidad.domains.joinToString(", ")}"
        }
    }
}


