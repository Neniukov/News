package com.example.news.common

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object DateExtension {

    const val SERVER_DATE_FORMAT = "yyyy-MM-dd"
    const val UI_DATE_FORMAT = "dd.MM.yyyy"
    const val FULL_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    const val ARTICLE_DATE_FORMAT = "HH:mm, dd-MM-yyyy"

    @SuppressLint("SimpleDateFormat")
    fun getDateString(date: Date, formatString: String): String {
        val format = SimpleDateFormat(formatString)
        return format.format(date)
    }

    @SuppressLint("SimpleDateFormat")
    fun getDataFromString(dateString: String, formatString: String): Date {
        val format = SimpleDateFormat(formatString)
        return format.parse(dateString) ?: Date()
    }

    @SuppressLint("SimpleDateFormat")
    fun formatToAnotherFormat(dateString: String, fromFormat: String, toFormat: String): String {
        val fromSDFormat = SimpleDateFormat(fromFormat)
        val toSDFormat = SimpleDateFormat(toFormat)
        val date = fromSDFormat.parse(dateString)
        return toSDFormat.format(date ?: Date())
    }

    fun Date.getFieldDate(field: Int): Int {
        val calendar = Calendar.getInstance().also { time = this.time }
        return calendar.get(field)
    }

    fun getMinDate(): Date {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, -20)
        return calendar.time
    }
}