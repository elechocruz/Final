package com.example.afinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.afinal.model.PeliculaItem
import com.google.android.material.snackbar.Snackbar
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_peliculas.*
import org.json.JSONArray
import org.json.JSONObject

class Peliculas : AppCompatActivity() {

    private val dataList: MutableList<PeliculaItem> = mutableListOf()
    private lateinit var myAdapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_peliculas)

        myAdapter = Adapter(dataList)

        my_recycler_view.layoutManager = LinearLayoutManager(this)
        my_recycler_view.addItemDecoration(DividerItemDecoration(this, OrientationHelper.VERTICAL))
        my_recycler_view.adapter = myAdapter


        val url = "https://wspelis.000webhostapp.com/Metodos/consultar_peliculas.php"
        val jsonObjectRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                Log.d("tag", "Response: %s".format(response.toString()))
                val gson = GsonBuilder().create()
                val Model= gson.fromJson(response.toString(),Array<PeliculaItem>::class.java).toList()
                dataList.addAll(Model)
                myAdapter.notifyDataSetChanged()
            },
            Response.ErrorListener { error ->
                // TODO: Handle error
            }
        )

        // Access the RequestQueue through your singleton class.
        Volley.newRequestQueue(this).add(jsonObjectRequest)

    }
}

