package com.example.afinal

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_perfil.*

class Perfil : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)
    }

    override fun onStart() {
        super.onStart()
        Log.e("asd","entro xd ")
        var texto: String = getIntent().getStringExtra("Nombre");
        tvNombre.text = texto;
        Log.e("asd",texto)
        var texto2: String = getIntent().getStringExtra("Sexo");
        tvSexo.text = texto2;
        Log.e("asd",texto2)

    }

    fun imgClick(view: View) {
        dispatchTakePictureIntent()
    }
    val REQUEST_IMAGE_CAPTURE = 1

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            ivFotoPerfil.setImageBitmap(imageBitmap)
        }
    }

}
