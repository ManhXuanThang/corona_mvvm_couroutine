package dev.best.covidkotlin.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import dev.best.covidkotlin.data.model.Country
import dev.best.covidkotlin.data.model.Global

@Dao
interface CovidDao {
    @Insert(onConflict = REPLACE)
    suspend fun update(country: List<Country>, global: Global)


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCountry(country: List<Country>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGlobal(global: Global)

    @Query("SELECT * FROM global")
    suspend fun getAllDataGlobal(): Global?

    @Query("SELECT * FROM country")
    suspend fun getAllDataCountry(): List<Country>?
}