package ru.aholmanov.push_notification_app.api

import io.reactivex.Completable
import retrofit2.http.Body
import retrofit2.http.POST
import ru.aholmanov.push_notification_app.model.PushNotification

interface PushService {

    @POST("/1/messages.json")
    fun pushNotification(@Body pushNotification: PushNotification): Completable
}