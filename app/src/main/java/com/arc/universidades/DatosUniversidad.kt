package com.arc.universidades

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class DatosUniversidad : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datos_universidad)

        // Obtén la información de la universidad desde el Intent
        val universidad = intent.getSerializableExtra("universidad") as? Universidad
        universidad?.let {
            // Actualiza las vistas con la información de la universidad
            findViewById<TextView>(R.id.tvNombreUniversidad).text = "Nombre: ${it.name}"
            findViewById<TextView>(R.id.tvPais).text = "País: ${it.country}"
            findViewById<TextView>(R.id.tvEstado).text = "Estado: ${it.stateProvince ?: "N/A"}"
            findViewById<TextView>(R.id.tvAlphaCode).text = "Alpha Code: ${it.alphaCode}"

            val webPagesText = if (it.webPages.isNullOrEmpty()) "N/A" else it.webPages.joinToString(", ")
            findViewById<TextView>(R.id.tvWebPages).text = "Páginas Web: $webPagesText"

            val dominiosText = if (it.domains.isNullOrEmpty()) "N/A" else it.domains.joinToString(", ")
            findViewById<TextView>(R.id.tvDominios).text = "Dominios: $dominiosText"
        }
    }
}

