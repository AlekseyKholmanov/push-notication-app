package ru.aholmanov.push_notification_app.dataLayer.api

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST
import ru.aholmanov.push_notification_app.domain.model.PushRequest
import ru.aholmanov.push_notification_app.domain.model.PushResponce

interface PushService {

    @POST("/1/messages.json")
    fun pushNotification( @Body notification: PushRequest): Single<PushResponce>
}