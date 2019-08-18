package ru.aholmanov.push_notification_app.extension

import android.text.Editable
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter

val pattern: DateTimeFormatter = DateTimeFormat.forPattern("dd.MM.yyyy HH:mm")

fun DateTime.toStringFormat(): String {
    return this.toString(pattern)
}

fun String.toDateTime(): DateTime {
    return DateTime.parse(this, pattern)
}

fun Editable.characterCount(): Int {
    return this.toString().count()
}