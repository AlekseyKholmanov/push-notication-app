package ru.aholmanov.push_notification_app.storage

import io.reactivex.Completable
import org.joda.time.DateTime
import ru.aholmanov.push_notification_app.api.PushService
import ru.aholmanov.push_notification_app.model.PushNotification
import ru.aholmanov.push_notification_app.model.SavedNotification
import ru.aholmanov.push_notification_app.orm.PushDatabase
import ru.aholmanov.push_notification_app.orm.PushNotificationDao
import javax.inject.Inject

class PushNotificationRepository @Inject constructor(
    database: PushDatabase,
    private val pushService: PushService
) {
    private val dao: PushNotificationDao by lazy { database.pushNotificationDao()}

    fun sendNotification(notification: PushNotification): Completable {

        return pushService.pushNotification(notification)
//            .doOnSubscribe { dao.insert(SavedNotification(DateTime.now(), notification)) }
    }
}