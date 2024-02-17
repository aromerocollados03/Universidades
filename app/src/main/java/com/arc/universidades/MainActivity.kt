package com.arc.universidades

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.arc.universidades.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), androidx.appcompat.widget.SearchView.OnQueryTextListener{

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UniAdapter
    private var infoUni = mutableListOf<Universidad>()
    private val apiService: APIService by lazy {
        getRetrofit().create(APIService::class.java)
    }
    private lateinit var selectedCountry: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.svUni.setOnQueryTextListener(this)
        initRecyclerView()
        configurarSpinnerPaises()
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

    fun onItemClick(universidad: Universidad) {
        val intent = Intent(this, DatosUniversidad::class.java)
        intent.putExtra("universidad", universidad)
        startActivity(intent)
    }

    private fun initRecyclerView() {
        adapter = UniAdapter(infoUni, this)
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
            try {
                val response = apiService.getUniByName(selectedCountry, query).execute()

                withContext(Dispatchers.Main) {
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
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showError()
                }
            }
        }
    }


    private fun showError() {
        Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
    }



    private fun showError(message: String = "Ha ocurrido un error") {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun cargarUniversidades(country: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = apiService.getUniversitiesByCountry(country)
            try {
                val response = call.execute()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val universidades = response.body() ?: emptyList()
                        infoUni.clear()
                        infoUni.addAll(universidades)
                        adapter.notifyDataSetChanged()
                    } else {
                        showError("Error al obtener las universidades: ${response.message()}")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showError("Error al conectarse a la API: ${e.message}")
                }
            }
        }
    }

    private fun configurarSpinnerPaises() {
        val countriesAdapter = ArrayAdapter.createFromResource(
            this, R.array.paises, android.R.layout.simple_spinner_item
        )
        countriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerPaises.adapter = countriesAdapter

        binding.spinnerPaises.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedCountry = parent.getItemAtPosition(position) as String
                cargarUniversidades(selectedCountry)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

}