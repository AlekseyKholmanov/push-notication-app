package ru.aholmanov.push_notification_app.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PushNotification (
    @SerializedName("token")
    val token:String,

    @SerializedName("user")
    val userKey:String,

    @SerializedName("message")
    val message: String
): Serializable