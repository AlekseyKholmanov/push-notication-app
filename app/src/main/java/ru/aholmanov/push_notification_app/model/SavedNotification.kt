package ru.aholmanov.push_notification_app.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.time.DateTime

@Entity
data class SavedNotification(
    @PrimaryKey(autoGenerate = false)
    val dateTime: DateTime,

    @Embedded
    val notification: PushNotification
)