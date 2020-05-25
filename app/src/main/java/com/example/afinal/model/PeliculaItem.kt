package com.example.afinal.model


import com.google.gson.annotations.SerializedName

data class PeliculaItem(
    @SerializedName("ano")
    val ano: String,
    @SerializedName("caratula")
    val caratula: String,
    @SerializedName("gen1")
    val gen1: String,
    @SerializedName("gen2")
    val gen2: String,
    @SerializedName("num_resenas")
    val numResenas: String,
    @SerializedName("puntuacion")
    val puntuacion: String,
    @SerializedName("sinopsis")
    val sinopsis: String,
    @SerializedName("titulo")
    val titulo: String
)