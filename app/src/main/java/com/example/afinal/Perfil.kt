package com.example.afinal

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.content.FileProvider
import kotlinx.android.synthetic.main.activity_perfil.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class Perfil : AppCompatActivity() {

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        val sexo = resources.getStringArray(R.array.SpinnerSexo)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, sexo)
        spSexo.adapter = adapter

        val pais = resources.getStringArray(R.array.SpinnePais)

        val adapterPais = ArrayAdapter(this, android.R.layout.simple_spinner_item, pais)
        spPaises.adapter = adapterPais

        val g1 = resources.getStringArray(R.array.SpinnerGenero)

        val adapterg1 = ArrayAdapter(this, android.R.layout.simple_spinner_item, g1)
        spGenero1.adapter = adapterg1
        spGenero2.adapter = adapterg1
    }

    override fun onStart() {
        super.onStart()
        var texto: String = getIntent().getStringExtra("Nombre");
        etNombre.setText(texto)
        var texto2: String = getIntent().getStringExtra("Sexo");
        //tvSexo.text = texto2
    }

    fun imgClick(view: View) {
        dispatchTakePictureIntent()
    }
    val REQUEST_TAKE_PHOTO = 1

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.example.afinal.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            ivFotoPerfil.setImageBitmap(imageBitmap)
        }
    }
    lateinit var currentPhotoPath: String

    @Throws(IOException::class)
    private fun createImageFile(): File {

        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    fun btnGuardarClick(view: View) {

    }
}
