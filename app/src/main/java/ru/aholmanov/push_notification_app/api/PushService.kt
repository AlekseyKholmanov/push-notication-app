package ru.aholmanov.push_notification_app.api

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST
import ru.aholmanov.push_notification_app.model.PushRequest
import ru.aholmanov.push_notification_app.model.PushResponce

interface PushService {

    @POST("/1/messages.json")
    fun pushNotification( @Body notification: PushRequest): Single<PushResponce>
}