package dev.best.covidkotlin.data.model

data class SumaryResponse(
    val Countries: List<Country>,
    val Date: String,
    val Global: Global,
    val Message: String
)