package com.example.afinal

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.afinal.model.PeliculaItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_pelicula.view.*

class Adapter(private val dataList: MutableList<PeliculaItem>) : RecyclerView.Adapter<Holder>(){
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        context = parent.context
        return Holder(LayoutInflater.from(context).inflate(R.layout.item_pelicula, parent, false))
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val data = dataList[position]

        val titulo = holder.itemView.tvTitulo
        val poster = holder.itemView.imageView
        val fecha = holder.itemView.tvAno
        val genero = holder.itemView.tvGeneros

        titulo.text = data.titulo
        fecha.text = data.ano
        genero.text = "${data.gen1} , ${data.gen2}"

        //Picasso.get().load("https://wspelis.000webhostapp.com/Imagenes/${data.caratula}").into(poster)
        Glide.with(context).load("https://wspelis.000webhostapp.com/Imagenes/${data.caratula}").into(poster)

        holder.itemView.setOnClickListener{
            val intent: Intent = Intent(context, DetallesPelicula::class.java)

            intent.putExtra("Titulo", data.titulo)
            intent.putExtra("Fecha", data.ano)
            intent.putExtra("Sinopsis", data.sinopsis)
            intent.putExtra("Portada", data.caratula)
            startActivity(context, intent, null)
        }
    }
}