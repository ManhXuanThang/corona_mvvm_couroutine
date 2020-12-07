package dev.best.covidkotlin.data.remote

import dev.best.covidkotlin.data.model.DataHalfMonthByCountry
import dev.best.covidkotlin.data.model.SumaryResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("summary")
    suspend fun getAllData(): SumaryResponse

    @GET("country/{country_name}")
    suspend fun getDataHalfMonthByCountry(
        @Path(value = "country_name") countryName: String,
        @Query(value = "from") fromDate: String,
        @Query(value = "to") toDate: String
    ): DataHalfMonthByCountry
}