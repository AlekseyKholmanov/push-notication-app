package ru.aholmanov.push_notification_app.storage

import android.util.Log
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import ru.aholmanov.push_notification_app.api.PushService
import ru.aholmanov.push_notification_app.model.PushRequest
import ru.aholmanov.push_notification_app.model.PushResponce
import ru.aholmanov.push_notification_app.model.SavedNotification
import ru.aholmanov.push_notification_app.orm.PushDatabase
import ru.aholmanov.push_notification_app.orm.PushNotificationDao
import javax.inject.Inject

class PushNotificationRepository @Inject constructor(
    database: PushDatabase,
    private val pushService: PushService
) {
    private val dao: PushNotificationDao = database.pushNotificationDao()

    fun sendNotification(notification: PushRequest): Single<PushResponce> {
        return pushService.pushNotification(notification = notification)
    }

    fun getNotification(): Single<List<SavedNotification>> {
        return dao.getAll()
    }

    fun insert(notification: SavedNotification): Completable {
        Log.d("qwerty","try to insert")
        return Completable.fromCallable { dao.insert(notification) }
    }

    fun observeNotifications(): Flowable<List<SavedNotification>> {
        return dao.observeNotifications()
            .map { it -> it.sortedByDescending { it.dateTime } }
    }
}