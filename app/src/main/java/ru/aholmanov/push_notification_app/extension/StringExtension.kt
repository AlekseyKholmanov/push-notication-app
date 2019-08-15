package ru.aholmanov.push_notification_app.extension

import android.text.Editable
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

fun DateTime.toFormatTime(): String {
    val pattern = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm")
    return this.toString(pattern)
}

fun Editable.characterCount(): Int {
    return this.toString().toByteArray(Charsets.UTF_8).count()
}