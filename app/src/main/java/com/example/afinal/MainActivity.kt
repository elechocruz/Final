package com.example.afinal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKeys
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File


class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //ABRE
        if(File(filesDir,"Username.txt").exists()){

            val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
            val masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)

            val fileToRead = "Username.txt"
            lateinit var byteStream: ByteArrayOutputStream
            val encryptedFile = EncryptedFile.Builder(
                File(filesDir, fileToRead),
                this,
                masterKeyAlias,
                EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
            ).build()

            val contents = encryptedFile.openFileInput().bufferedReader().useLines { lines ->
                lines.fold("") { working, line ->
                    "$working$line"
                }
            }
            etUsername.setText(contents.toString())
        }
    }

    fun btnLog(view: View) {
        if(File(filesDir,"Username.txt").exists()) {
            File(filesDir,"Username.txt").delete()
        }
            //GUARDA
        val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
        val masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)

        val fileToWrite = "Username.txt"
        val encryptedFile = EncryptedFile.Builder(
            File(filesDir, fileToWrite),
            this,
            masterKeyAlias,
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
        ).build()

        encryptedFile.openFileOutput().bufferedWriter().use {writer ->
            writer.write(etUsername.text.toString())
        }
        var Nombre = ""
        var Sexo = ""

        val url = "https://wspelis.000webhostapp.com/Metodos/login.php"

        val jsonObject = JSONObject()
        jsonObject.put("usuariolg", etUsername.text)
        jsonObject.put("passlg", etPassword.text)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, jsonObject,
            Response.Listener { response ->
                Log.d("tag", "Response: %s".format(response.toString()))
                Snackbar.make(view, "Bienvenido! Usuario: ${response["Nombre"].toString()}", Snackbar.LENGTH_SHORT).show()
                Nombre = response["Nombre"].toString()
                Sexo = response["Sexo"].toString()

                val intent: Intent = Intent(this, Perfil::class.java)

                intent.putExtra("Nombre",Nombre)
                intent.putExtra("Sexo",Sexo)
                startActivity(intent)
            },
            Response.ErrorListener { error ->
                // TODO: Handle error
                Snackbar.make(view, "Error al conectar, el usuario no existe!", Snackbar.LENGTH_SHORT).show()
            }
        )

        // Access the RequestQueue through your singleton class.
        Volley.newRequestQueue(this).add(jsonObjectRequest)
    }

    fun btnReg(view: View) {
        val url = "https://wspelis.000webhostapp.com/Metodos/registrar.php"

        val jsonObject = JSONObject()
        jsonObject.put("usuario", etUsername.text)
        jsonObject.put("pass", etPassword.text)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, jsonObject,
            Response.Listener { response ->
                Log.d("tag", "Response: %s".format(response.toString()))
                //Snackbar.make(view, "Usuario: ${response["Nombre"].toString()} registrado!", Snackbar.LENGTH_SHORT).show()
            },
            Response.ErrorListener { error ->
                // TODO: Handle error
                Log.e("asd",error.toString())
                //Snackbar.make(view, "Error al conectar, el usuario no existe!", Snackbar.LENGTH_SHORT).show()
            }
        )

        // Access the RequestQueue through your singleton class.
        Volley.newRequestQueue(this).add(jsonObjectRequest)
    }
}
interface VolleyListener {
    fun requestFinished(exsitance: Boolean)
}


