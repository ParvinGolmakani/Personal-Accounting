package com.pg.personalaccounting.core.utils

import android.annotation.SuppressLint
import android.os.Build
import android.text.format.DateFormat
import androidx.annotation.RequiresApi
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


fun longToDate(milliSecond: Long): String {
    return DateFormat.format(AppConstants.dateFormat, Date(milliSecond)).toString()
}

@SuppressLint("SimpleDateFormat")
@RequiresApi(Build.VERSION_CODES.N)
fun dateToLong(dateString: String): Long {
    return try {

        val sdf = SimpleDateFormat(AppConstants.dateFormat)
        val date: Date = sdf.parse(dateString)
        date.time
    } catch (e: ParseException) {
        e.printStackTrace()
        11
    }
}

fun getStringDate(year: String, month: String, day: String): String {
    return "$month/$day/$year"
}