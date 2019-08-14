package ru.aholmanov.push_notification_app.extension

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

fun DateTime.toFormatTime():String{
    val pattern = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm")
    return this.toString(pattern)
}