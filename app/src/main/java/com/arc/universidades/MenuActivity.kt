package com.arc.universidades

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arc.universidades.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnToApp.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.btnToSharedPreferences.setOnClickListener {
            startActivity(Intent(this, SharedPreferences::class.java))
        }
    }
}
