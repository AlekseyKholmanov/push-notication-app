package ru.aholmanov.push_notification_app.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PushResponce (
    @SerializedName("status")
    val status:Int,

    @SerializedName("request")
    val requestId: String,

    @SerializedName("errors")
    val error: Array<String>?
):Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PushResponce

        if (status != other.status) return false
        if (requestId != other.requestId) return false
        if (error != null) {
            if (other.error == null) return false
            if (!error.contentEquals(other.error)) return false
        } else if (other.error != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = status
        result = 31 * result + requestId.hashCode()
        result = 31 * result + (error?.contentHashCode() ?: 0)
        return result
    }
}
