package com.pg.personalaccounting.core.utils

import android.icu.text.SimpleDateFormat
import android.os.Build
import android.text.format.DateFormat
import androidx.annotation.RequiresApi
import java.text.ParseException
import java.util.*


fun longToDate(milliSecond: Long): String {
    return DateFormat.format("MM/dd/yyyy", Date(milliSecond)).toString()
}

@RequiresApi(Build.VERSION_CODES.N)
fun dateToLong(dateString: String): Long {
    return try {
        val sdf = SimpleDateFormat("MM/dd/yyyy")
        val date: Date = sdf.parse(dateString)
        date.time
    } catch (e: ParseException) {
        e.printStackTrace()
        11
    }
}