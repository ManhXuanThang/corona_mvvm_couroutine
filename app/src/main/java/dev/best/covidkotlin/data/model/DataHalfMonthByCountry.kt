package dev.best.covidkotlin.data.model

class DataHalfMonthByCountry : ArrayList<DataHalfMonthByCountryItem>()

data class DataHalfMonthByCountryItem(
    val Confirmed: Int,
    val Deaths: Int,
    val Recovered: Int,
    val Active: Int,
    val City: String,
    val CityCode: String,
    val Country: String,
    val CountryCode: String,
    val Date: String,
    val Lat: String,
    val Lon: String,
    val Province: String

)

