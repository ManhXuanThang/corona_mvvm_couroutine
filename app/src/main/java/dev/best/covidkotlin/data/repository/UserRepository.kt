package dev.best.covidkotlin.data.repository

import dev.best.covidkotlin.data.model.Country
import dev.best.covidkotlin.data.model.DataHalfMonthByCountry
import dev.best.covidkotlin.data.model.Global
import dev.best.covidkotlin.data.model.SumaryResponse

interface UserRepository {
    //service
    suspend fun getAllDataFromService(): SumaryResponse

    //local
    suspend fun getDataGlobal(): Global?

    suspend fun updateDB(global: Global, listCountry: List<Country>)

    suspend fun insertListCountry(listCountry: List<Country>)

    suspend fun getDataCountry(): List<Country>?

    suspend fun getHalfMonthByCountry(
        countryName: String,
        fromDate: String,
        toDate: String
    ): DataHalfMonthByCountry
}
