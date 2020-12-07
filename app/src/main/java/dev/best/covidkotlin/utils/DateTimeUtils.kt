package dev.best.covidkotlin.utils

import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtils {
    const val yyyyMMddTHHmmssZ = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    const val yyyyMMddHHmmss = "yyyyMMddHHmmss"
    const val eeeeddMMyyyhhmmaa = "EEEE, dd/MM/yyyy HH:mm"
    const val ddMMyy = "dd/MM/yyyy"
    const val yyyyMMdd = "yyyyMMdd"


    fun formatDate(patern: String, date: Date?): String {
        val yyyyMMddTHHmmssZ = SimpleDateFormat(patern, Locale.US)
        return yyyyMMddTHHmmssZ.format(date)
    }
}