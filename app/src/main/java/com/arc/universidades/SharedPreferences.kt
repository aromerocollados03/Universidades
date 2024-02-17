package com.arc.universidades

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate

class SharedPreferences : AppCompatActivity() {
    private var numero = 0
    private lateinit var tvDato: TextView
    private lateinit var spinnerColorFavorito: Spinner

    @SuppressLint("MissingInflatedId", "UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        aplicarTema()
        setContentView(R.layout.activity_shared_preferences)

        tvDato = findViewById(R.id.tvDato)
        val botonGuardar = findViewById<Button>(R.id.botonGuardar)
        val botonDecrementar = findViewById<Button>(R.id.botonDecrementar)
        val botonResetear = findViewById<Button>(R.id.botonResetear)

        val pref = applicationContext.getSharedPreferences("datos", MODE_PRIVATE)
        numero = pref.getInt("contador", 0)
        tvDato.text = numero.toString()

        botonGuardar.setOnClickListener {
            modificarValor(1)
        }

        botonDecrementar.setOnClickListener {
            modificarValor(-1)
        }

        botonResetear.setOnClickListener {
            modificarValor(0, true)
        }

        spinnerColorFavorito = findViewById(R.id.spinnerColorFavorito)
        inicializarSpinnerColor()

        cargarPreferencias()
    }

    private fun modificarValor(cambio: Int, reset: Boolean = false) {
        numero = if (reset) 0 else numero + cambio
        tvDato.text = numero.toString()

        val editor = applicationContext.getSharedPreferences("datos", MODE_PRIVATE).edit()
        editor.putInt("contador", numero)
        editor.apply()
    }

    private fun aplicarTema() {
        val pref = getSharedPreferences("prefs", MODE_PRIVATE)
        if (pref.getBoolean("modoOscuro", false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun cambiarTema(modoOscuro: Boolean) {
        val editor = getSharedPreferences("prefs", MODE_PRIVATE).edit()
        editor.putBoolean("modoOscuro", modoOscuro)
        editor.apply()

        // Reaplica el tema
        if (modoOscuro) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun esModoOscuroActivado(): Boolean {
        val pref = getSharedPreferences("prefs", MODE_PRIVATE)
        return pref.getBoolean("modoOscuro", false)
    }

    private fun inicializarSpinnerColor() {
        val colores = listOf("Rojo", "Verde", "Azul", "Amarillo", "Naranja")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, colores)
        spinnerColorFavorito.adapter = adapter

        spinnerColorFavorito.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                // El parámetro 'view' ahora es nullable (View?), lo que permite manejar situaciones donde puede ser nulo
                val colorSeleccionado = parent.getItemAtPosition(position).toString()
                guardarPreferenciaColor(colorSeleccionado)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Opcional: Manejar ningún elemento seleccionado
            }
        }
    }

    private fun cargarPreferencias() {
        val pref = getSharedPreferences("prefs", MODE_PRIVATE)
        val colorFavorito = pref.getString("colorFavorito", "Rojo") // Valor por defecto: Rojo
        spinnerColorFavorito.setSelection((spinnerColorFavorito.adapter as ArrayAdapter<String>).getPosition(colorFavorito))
        // Asegúrate de cargar también las otras preferencias
    }

    private fun guardarPreferenciaColor(color: String) {
        val editor = getSharedPreferences("prefs", MODE_PRIVATE).edit()
        editor.putString("colorFavorito", color)
        editor.apply()
    }
}

