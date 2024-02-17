package com.arc.universidades

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.room.Room
import com.arc.universidades.databinding.ActivityLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding // Asume que usas View Binding
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "midb"
        ).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnRegistrarse = findViewById<Button>(R.id.btnRegistro)
        btnRegistrarse.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            verificarCredenciales(username, password)
        }
    }

    private fun verificarCredenciales(username: String, password: String) {
        // Suponiendo que estás utilizando coroutines para consultar en la base de datos
        CoroutineScope(Dispatchers.IO).launch {
            val usuario = db.usuarioDao().buscarUsuario(username, password)
            withContext(Dispatchers.Main) {
                if (usuario != null) {
                    // Usuario encontrado, login exitoso
                    iniciarMenuActivity()
                } else {
                    // Usuario no encontrado, login fallido
                    Toast.makeText(applicationContext, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun iniciarMenuActivity() {
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
        finish()
    }


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
