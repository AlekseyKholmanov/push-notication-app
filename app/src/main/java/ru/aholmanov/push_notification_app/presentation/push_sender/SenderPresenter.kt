package ru.aholmanov.push_notification_app.presentation.push_sender

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import org.joda.time.DateTime
import org.json.JSONObject
import retrofit2.HttpException
import ru.aholmanov.push_notification_app.extension.async
import ru.aholmanov.push_notification_app.model.Priority
import ru.aholmanov.push_notification_app.model.PushRequest
import ru.aholmanov.push_notification_app.model.SavedNotification
import ru.aholmanov.push_notification_app.mvp.BasePresenter
import ru.aholmanov.push_notification_app.network.NetworkStateListener
import ru.aholmanov.push_notification_app.storage.PushNotificationRepository
import javax.inject.Inject


@InjectViewState
class SenderPresenter @Inject constructor(
    private val repository: PushNotificationRepository,
    private val networkStateListener: NetworkStateListener
) : BasePresenter<SenderView>() {
    private var previousInternetState = true

    fun sentNotification(
        user: String,
        message: String,
        title: String,
        priority: Priority,
        retry: String,
        expire: String
    ) {
        val notification = PushRequest(
            userKey = user,
            message = message,
            title = title,
            priority = priority.id,
            retry = retry,
            expire = expire
        )
        viewState.showLoading(true)
        repository.sendNotification(notification)
            .async()
            .doAfterTerminate { viewState.showLoading(false) }
            .subscribe({ response ->
                viewState.showSuccess()
                insert(id = response.requestId, notification = notification, isSuccess = true)
            }, { error ->
                val pair = getErrorComponent(error)
                viewState.showError(pair.first)
                insert(id = pair.second, notification = notification, isSuccess = false)
            })
            .keep()
    }

    private fun getErrorComponent(t: Throwable?): Pair<String, String> {
        var (message, id) = Pair("", "")
        if (t is HttpException) {
            val body = t.response().errorBody()
            val jObjError = JSONObject(body!!.string())
            message = jObjError.getJSONArray("errors").getString(0)
            id = jObjError.getString("request")
        } else {
            message = "unexpected error"
            id = "0"
        }
        return message to id
    }

    fun subscribeToNetworkChanges() {
        networkStateListener.observeNetworkState()
            .filter { it != previousInternetState }
            .doOnNext {
                previousInternetState = it
            }
            .subscribe { connected ->
                viewState.onInternetStateChanged(connected)
            }
            .keep()
    }

    private fun insert(id: String, notification: PushRequest, isSuccess: Boolean) {
        repository.insert(SavedNotification(id, DateTime.now(), notification, isSuccess))
            .async()
            .subscribe({}, {
                Log.d("qwerty", it.message)
            })
            .keep()
    }
}