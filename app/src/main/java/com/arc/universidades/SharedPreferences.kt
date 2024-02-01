package com.arc.universidades

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class SharedPreferences : AppCompatActivity() {
    var numero = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shared_preferences)

        val tvDato = findViewById<TextView>(R.id.tvDato)
        val botonGuardar = findViewById<Button>(R.id.botonGuardar)

        val pref = applicationContext.getSharedPreferences("datos", 0)
        numero = pref.getInt("contador", 0)
        tvDato.text = numero.toString()

        botonGuardar.setOnClickListener {
            numero++
            tvDato.text = numero.toString()

            val editor = pref.edit()
            editor.putInt("contador", numero)
            editor.apply()
        }
    }
}
