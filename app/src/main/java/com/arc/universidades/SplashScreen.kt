package com.arc.universidades

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ProgressBar


@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.visibility = View.VISIBLE
        cargarDatos()
    }

    private fun cargarDatos() {
        Handler().postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}
