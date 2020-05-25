package com.example.afinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detalles_pelicula.*

class DetallesPelicula : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_pelicula)

        val Titulo = getIntent().getStringExtra("Titulo")
        val Fecha = getIntent().getStringExtra("Fecha")
        val Sinopsis = getIntent().getStringExtra("Sinopsis")
        val Portada = getIntent().getStringExtra("Portada")

        tvTitulo.text = "${Titulo} (${Fecha})"
        tvSinopsis.text = Sinopsis
        Glide.with(this).load("https://wspelis.000webhostapp.com/Imagenes/${Portada}").into(ivPortada)

    }
}
