package com.example.afinal

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.os.Bundle
import android.provider.ContactsContract
import android.telephony.SmsManager
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.example.afinal.model.PeliculaItem
import com.example.afinal.room.DBLocal
import kotlinx.android.synthetic.main.activity_detalles_pelicula.*


class DetallesPelicula : AppCompatActivity() {

    lateinit var Titulo: String
    lateinit var Fecha: String
    lateinit var Sinopsis: String
    lateinit var Portada: String
    lateinit var context: Context


    private val requestSendSMS = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_pelicula)

        context = this
        Titulo = getIntent().getStringExtra("Titulo")
        Fecha = getIntent().getStringExtra("Fecha")
        Sinopsis = getIntent().getStringExtra("Sinopsis")
        Portada = getIntent().getStringExtra("Portada")

        tvTitulo.text = "${Titulo} (${Fecha})"
        tvSinopsis.text = Sinopsis
        Glide.with(this).load("https://wspelis.000webhostapp.com/Imagenes/${Portada}").into(ivPortada)
        Contactos()
    }

    fun btnMarcarVisto(view: View) {
        Log.e("asdasd", "Si entro aqui we ")
        InsertaDB(application, PeliculaItem(Fecha,Portada, "null", "null", "0", "0", "asd", Titulo)).execute()
    }

    fun Contactos(){
        val contactos: MutableList<String> = ArrayList()

        val cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)
        if(cursor != null && cursor.moveToFirst()){
            do{
                val nombre = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                Log.d("contact", "name: " + nombre)
                contactos.add(nombre)
            }while (cursor.moveToNext())
            cursor.close()
        }
        val adapter = ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, contactos)
        acContactos.setAdapter(adapter)
        Log.e("asdasdasd",contactos.toString())
    }

    fun btnMandarRecomendacion(view: View) {
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.SEND_SMS), requestSendSMS)
        }else{
            enviaSMS()
        }
    }
    private fun enviaSMS(){
        var numero: String = ""
        var ID: String = ""
        val NombreContacto = acContactos.selectedItem.toString()
        val cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)
        if(cursor != null && cursor.moveToFirst()){
            do{
                val nombre = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                if(nombre == NombreContacto){
                    ID = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                }
            }while (cursor.moveToNext())
            cursor.close()
        }

        val cursorPhone = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER),
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                    ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                    ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,
            arrayOf(ID),
            null

        )

        if (cursorPhone != null) {
            if (cursorPhone.moveToFirst()) {
                numero = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            }
        }

        cursorPhone?.close()
        SmsManager.getDefault().sendTextMessage(numero, null,"Hola! ${NombreContacto} te recomiendo que veas la pelicula: ${tvTitulo.text}",null,null)
        Log.e("numero", numero)
    }
}

class InsertaDB(val application: Application, val peli: PeliculaItem) : AsyncTask<Void, Void, Void>(){
    override fun doInBackground(vararg params: Void?): Void? {
        DBLocal.get(application).getDao().insertPelicula(peli)
        val resultado = DBLocal.get(application).getDao().consultaPeliculas()
        Log.e("asd", "${resultado[0].titulo} ${resultado[1].titulo}")
        return null
    }
}


