package ru.aholmanov.push_notification_app.orm

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable
import io.reactivex.Single
import ru.aholmanov.push_notification_app.model.SavedNotification


@Dao
interface PushNotificationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(notification: SavedNotification)

    @Query("SELECT * FROM savednotification")
    fun getAll(): Single<List<SavedNotification>>

    @Query("SELECT * FROM savednotification")
    fun observeNotifications():Flowable<List<SavedNotification>>
}