package com.arc.universidades

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.room.Room
import com.arc.universidades.databinding.ActivityRegistroBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegistroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistroBinding
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "midb"
        ).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegistrar.setOnClickListener {
            val username = binding.etRegistroUsername.text.toString()
            val password = binding.etRegistroPassword.text.toString()
            val email = binding.etRegistroEmail.text.toString()
            registrarUsuario(username, password, email)
        }
    }

    private fun registrarUsuario(username: String, password: String, email: String) {
        if (username.isNotEmpty() && password.isNotEmpty() && email.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                val usuario = Usuario(nombre = username, passwd = password, correo = email)
                db.usuarioDao().insertarUsuario(usuario)
                withContext(Dispatchers.Main) {
                    Toast.makeText(applicationContext, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show()
                    finish() // Cierra la actividad y vuelve a la pantalla de login
                }
            }
        } else {
            Toast.makeText(this, "Por favor, rellena todos los campos", Toast.LENGTH_SHORT).show()
        }
    }
}

