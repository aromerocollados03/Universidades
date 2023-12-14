package com.arc.universidades

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.arc.universidades.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), androidx.appcompat.widget.SearchView.OnQueryTextListener{

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UniAdapter
    private var infoUni = mutableListOf<Universidad>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.svUni.setOnQueryTextListener(this)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = UniAdapter(infoUni)
        binding.rvUni.layoutManager = LinearLayoutManager(this)
        binding.rvUni.adapter = adapter
    }

    private fun getRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("http://universities.hipolabs.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun searchByName(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response =
                getRetrofit().create(APIService::class.java).getUniByName("Spain", query).execute()

            runOnUiThread {
                if (response.isSuccessful) {
                    val universities = response.body()
                    universities?.let {
                        infoUni.clear()
                        infoUni.addAll(it)
                        adapter.notifyDataSetChanged()
                    }
                } else {
                    showError()
                }
            }

        }

    }

    private fun showError() {
        Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrEmpty()) {
            searchByName(query.toLowerCase())
        }
        return true
    }


    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }
}