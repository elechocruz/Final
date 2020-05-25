package com.example.afinal.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.afinal.model.PeliculaItem

@Dao
interface LocalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPelicula(vararg peli: PeliculaItem)

    @Query("SELECT * FROM PeliculaItem")
    fun consultaPeliculas() : Array<PeliculaItem>
}