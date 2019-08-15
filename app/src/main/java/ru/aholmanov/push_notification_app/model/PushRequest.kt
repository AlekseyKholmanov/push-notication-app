package ru.aholmanov.push_notification_app.model

import com.google.gson.annotations.SerializedName
import ru.aholmanov.push_notification_app.BuildConfig
import java.io.Serializable

data class PushRequest(

    @SerializedName("user")
    val userKey: String,

    @SerializedName("message")
    val message: String,

    @SerializedName("title")
    val title: String?,

    @SerializedName("priority")
    val priority: Int,

    @SerializedName("retry")
    val retry: String?,

    @SerializedName("expire")
    val expire: String?

) : Serializable