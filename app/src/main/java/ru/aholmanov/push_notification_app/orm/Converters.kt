package ru.aholmanov.push_notification_app.orm

import androidx.room.TypeConverter
import org.joda.time.DateTime


class Converters {
    @TypeConverter
    fun fromDateTime(dateTime: DateTime): Long {
        return dateTime.millis
    }

    @TypeConverter
    fun dateToDateTime(date: Long): DateTime {
        return DateTime(date)
    }
}