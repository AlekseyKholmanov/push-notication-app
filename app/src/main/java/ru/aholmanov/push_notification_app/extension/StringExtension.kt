package ru.aholmanov.push_notification_app.extension

import android.text.Editable
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

fun DateTime.toStringFormat(): String {
    val pattern = DateTimeFormat.forPattern("dd.MM.yyyy HH:mm")
    return this.toString(pattern)
}

fun String.toDateTime():DateTime{
    val pattern = DateTimeFormat.forPattern("dd.MM.yyyy HH:mm")
    return DateTime.parse(this,pattern)
}

fun Editable.characterCount(): Int {
    return this.toString().count()
}