package dev.best.covidkotlin.utils

import android.text.TextUtils
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.ParseException
import java.util.*

object NumberFormatUtils {
    @JvmStatic
    fun formatNumber(text: String): String {
        val df = DecimalFormat("#,###.##")
        return df.format(removeSeparatorNumber(text))
    }

    @JvmStatic
    fun formatNumber(text: Int): String {
        val df = DecimalFormat("#,###.##")
        return df.format(text)
    }

    @JvmStatic
    fun formatNumber(text: Float): String {
        val df = DecimalFormat("#,###.##")
        return df.format(text.toDouble())
    }

    @JvmStatic
    fun formatNumber(text: Double): String {
        val df = DecimalFormat("#,###.##")
        return df.format(text)
    }

    @JvmStatic
    fun removeSeparatorNumber(text: String?): Long {
        if (text.isNullOrEmpty()) return 0
        val df = DecimalFormat("#,###.##")
        val v = text.replace(df.decimalFormatSymbols.groupingSeparator.toString(), "")
        var n: Number = 0
        try {
            n = df.parse(v)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return n.toLong()
    }

    @JvmStatic
    fun removeSeparatorNumberToLong(text: String?): Long {
        if (text.isNullOrEmpty()) return 0
        val df = DecimalFormat("#,###")
        val v = text.replace(df.decimalFormatSymbols.groupingSeparator.toString(), "")
        var n: Number = 0
        try {
            n = df.parse(v)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return n.toLong()
    }

    /**
     * 123,123,123,123.00
     *
     * @param text
     * @return
     */
    @JvmStatic
    fun removeSeparatorNumberLocaleUS(text: String?): Long {
        if (text.isNullOrEmpty()) return 0
        val symbols = DecimalFormatSymbols(Locale.US)
        symbols.decimalSeparator = '.'
        symbols.groupingSeparator = ','

        val df = DecimalFormat("#,###.##", symbols)
        val v = text.replace(
            df.decimalFormatSymbols.groupingSeparator.toString(), ""
        )
        var n: Number = 0
        try {
            n = df.parse(v)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return n.toLong()
    }


}