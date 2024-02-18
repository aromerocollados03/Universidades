package com.arc.universidades

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.room.Room
import com.arc.universidades.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "midatabase"
        ).build()
    }
    private lateinit var etPassword: EditText
    private lateinit var checkBoxShowPassword: CheckBox
    private lateinit var googleSignInClient: GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnRegistrarse = findViewById<Button>(R.id.btnRegistro)
        btnRegistrarse.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }

        etPassword = findViewById(R.id.etPassword)
        checkBoxShowPassword = findViewById(R.id.checkBoxShowPassword)

        checkBoxShowPassword.setOnCheckedChangeListener { _, isChecked ->
            visibilidadContrasena(isChecked)
        }

        binding.btnLogin.setOnClickListener {
            iniciarSesion()
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        val btnLoginWithGoogle = findViewById<Button>(R.id.btnLoginWithGoogle)
        btnLoginWithGoogle.setOnClickListener {
            signInWithGoogle()
        }
    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    companion object { private const val RC_SIGN_IN = 9001 }

    private fun iniciarSesion() {
        val username = binding.etUsername.text.toString()
        val password = binding.etPassword.text.toString()

        if (username.isBlank() || password.isBlank()) {
            Toast.makeText(this, "Por favor, ingresa tu usuario y contraseña", Toast.LENGTH_SHORT).show()
            return
        }

        val progressDialog = AlertDialog.Builder(this)
            .setTitle("Iniciando sesión")
            .setMessage("Por favor, espera...")
            .setCancelable(false)
            .setView(ProgressBar(this))
            .create()

        progressDialog.show()

        Handler(Looper.getMainLooper()).postDelayed({
            verificarCredenciales(username, password, progressDialog)
        }, 3000)
    }

    private fun verificarCredenciales(username: String, password: String, progressDialog: AlertDialog) {
        CoroutineScope(Dispatchers.IO).launch {
            val usuario = db.usuarioDao().buscarUsuario(username, password)
            withContext(Dispatchers.Main) {
                progressDialog.dismiss()
                if (usuario != null) {
                    iniciarMenuActivity()
                } else {
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

    private fun visibilidadContrasena(isChecked: Boolean) {
        if (isChecked) {
            etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
        } else {
            etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    iniciarMenuActivity()
                } else {
                    Toast.makeText(this, "Fallo en la autenticación.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Log.e("LoginActivity", "Inicio sesión con google fallido", e)
            }
        }
    }

}
