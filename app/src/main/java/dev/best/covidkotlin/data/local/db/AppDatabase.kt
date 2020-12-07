package dev.best.covidkotlin.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.best.covidkotlin.data.local.dao.CovidDao
import dev.best.covidkotlin.data.model.Country
import dev.best.covidkotlin.data.model.Global

@Database(entities = [Country::class, Global::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun covidDao(): CovidDao
}