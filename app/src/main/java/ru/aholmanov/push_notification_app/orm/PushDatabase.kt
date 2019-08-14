package ru.aholmanov.push_notification_app.orm

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.aholmanov.push_notification_app.model.SavedNotification

@TypeConverters(Converters::class)
@Database(
    entities = [
        SavedNotification::class
    ],
    version = 1,
    exportSchema = false
)
abstract class PushDatabase :RoomDatabase(){
    abstract fun pushNotificationDao():PushNotificationDao
}