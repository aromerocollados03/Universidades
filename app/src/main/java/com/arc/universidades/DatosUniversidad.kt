package com.arc.universidades

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.arc.universidades.databinding.ActivityDatosUniversidadBinding

class DatosUniversidad : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDatosUniversidadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val universidad = intent.getSerializableExtra("universidad") as? Universidad
        universidad?.let { updateViews(binding, it) }

        binding.btnVolver.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
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


