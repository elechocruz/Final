package com.example.afinal.room

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.afinal.model.PeliculaItem

@Database(version = 1, entities = [PeliculaItem::class])
abstract class DBLocal : RoomDatabase(){
    companion object{
        fun get(application: Application): DBLocal{
            return Room.databaseBuilder(application, DBLocal::class.java,"PeliculasLocal").build()
        }
    }

    abstract fun getDao() : LocalDao
}