package vn.toanpv.sample.arena.extension.parser

import java.text.SimpleDateFormat
import java.util.*

fun Long.toISO8601Z(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    dateFormat.timeZone = TimeZone.getTimeZone("GMT")
    return dateFormat.format(Date(this))
}

fun String.fromISO8601Z(): Long {
    return try {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("GMT")
        dateFormat.parse(this).time
    } catch (ex: Exception) {
        ex.printStackTrace()
        0
    }
}

fun Long.toCalendar(): Calendar {
    val cal = Calendar.getInstance()
    cal.timeInMillis = this
    return cal
}

fun String.fromISO8601ZToCalendar(): Calendar? {
    val cal = Calendar.getInstance()
    cal.timeInMillis = fromISO8601Z()
    return cal
}

fun String.fromISO8601ZToHHmm(): String {
    val cal = fromISO8601Z()
    val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    dateFormat.timeZone = TimeZone.getDefault()
    return dateFormat.format(Date(cal))
}

fun String.fromISO8601ZToDDMM(): String {
    val cal = fromISO8601Z()
    val dateFormat = SimpleDateFormat("MM:dd", Locale.getDefault())
    dateFormat.timeZone = TimeZone.getDefault()
    return dateFormat.format(Date(cal))
}

fun Calendar.toDateFormat(pattern: String = "dd/MM"): String {
    val format = SimpleDateFormat(pattern, Locale.getDefault())
    return try {
        format.format(this.time)
    } catch (exception: Exception) {
        ""
    }
}

fun Calendar.toTimeFormat(pattern: String = "HH:mm"): String {
    val format = SimpleDateFormat(pattern, Locale.getDefault())
    return try {
        format.format(this.time)
    } catch (exception: Exception) {
        ""
    }
}