package uz.gita.todoapp.utils


import java.text.SimpleDateFormat
import java.util.*


fun getCurrentDate(): String {
    val c = Calendar.getInstance().time
    return SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(c).uppercase()
}

fun String.toDate(): Date {
    val format = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    format.parse(this)
    return format.calendar.time
}

fun getCurrentTime(): String {
    val c = Calendar.getInstance().time
    return SimpleDateFormat("HH:mm", Locale.getDefault()).format(c)
}

fun String.toTime(): List<String> {
    return this.split(":")
}