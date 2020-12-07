package dev.best.covidkotlin.data.local.pref

interface PrefHelper {

    fun isFirstRun(): Boolean

    fun remove(key: String)

    fun clear()

    fun setCountry(countryName:String)

    fun getCountry():String

}