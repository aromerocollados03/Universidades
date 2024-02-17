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
            AppDatabase::class.java, "midatabase"
        ).fallbackToDestructiveMigration()
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegistrar.setOnClickListener {
            val username = binding.etRegistroUsername.text.toString().trim()
            val password = binding.etRegistroPassword.text.toString().trim()
            val email = binding.etRegistroEmail.text.toString().trim()
            if (validarCampos(username, password, email)) {
                registrarUsuario(username, password, email)
            } else {
                Toast.makeText(this, "Por favor, rellena todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validarCampos(username: String, password: String, email: String): Boolean {
        // Aquí puedes añadir más lógica de validación si es necesario
        return username.isNotEmpty() && password.isNotEmpty() && email.isNotEmpty()
    }

    private fun registrarUsuario(username: String, password: String, email: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val usuario = Usuario(nombre = username, passwd = password, correo = email)
            db.usuarioDao().insertarUsuario(usuario)
            withContext(Dispatchers.Main) {
                mostrarMensaje("Usuario registrado con éxito")
                finish()
            }
        }
    }

    private fun mostrarMensaje(mensaje: String) {
        Toast.makeText(applicationContext, mensaje, Toast.LENGTH_SHORT).show()
    }
}


