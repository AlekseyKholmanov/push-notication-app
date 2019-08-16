package ru.aholmanov.push_notification_app.dataLayer.storage

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import ru.aholmanov.push_notification_app.dataLayer.api.PushService
import ru.aholmanov.push_notification_app.domain.model.PushRequest
import ru.aholmanov.push_notification_app.domain.model.PushResponce
import ru.aholmanov.push_notification_app.domain.model.SavedNotification
import ru.aholmanov.push_notification_app.dataLayer.orm.PushDatabase
import ru.aholmanov.push_notification_app.dataLayer.orm.PushNotificationDao
import javax.inject.Inject

class PushNotificationRepository @Inject constructor(
    database: PushDatabase,
    private val pushService: PushService
) {
    private val dao: PushNotificationDao = database.pushNotificationDao()

    fun sendNotification(notification: PushRequest): Single<PushResponce> {
        return pushService.pushNotification(notification = notification)
    }

    fun insert(notification: SavedNotification): Completable {
        return Completable.fromCallable { dao.insert(notification) }
    }

    fun observeNotifications(): Flowable<List<SavedNotification>> {
        return dao.observeNotifications()
            .map { it -> it.sortedByDescending { it.dateTime } }
    }
}