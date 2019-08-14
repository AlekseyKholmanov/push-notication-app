package ru.aholmanov.push_notification_app.presentation.push_sender

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import org.joda.time.DateTime
import ru.aholmanov.push_notification_app.extension.async
import ru.aholmanov.push_notification_app.extension.toResult
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

    fun sentNotification(user: String, message: String) {
        val notification = PushRequest(userKey = user, message = message)
        viewState.showLoading(true)
        repository.sendNotification(notification)
            .async()
//            .doOnError {
//                insert(notification,false)
//                Log.d("qwerty", "error")
//            }
//            .doAfterSuccess {
//                insert(notification, true)
//                Log.d("qwerty", "success")
//            }
            .toResult()
            .doAfterTerminate { viewState.showLoading(false) }
            .subscribe({
                if (it.isSuccess())
                    viewState.showSuccess()
                else
                    viewState.showError("unexpected error")
                insert(notification,it.isSuccess())
            }, {}

//                { error ->
//                if (error is HttpException) {
//                    val body = error.response().errorBody()
//                    val jObjError = JSONObject(body!!.string())
//                    val errorMessage = jObjError.getJSONArray("errors").getString(0)
//                    viewState.showError(errorMessage)
//                }
//                else
//                    viewState.showError("unexpected error")
//            }
            )
            .keep()
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

    private fun insert(notification: PushRequest, isSuccess: Boolean) {
        repository.insert(SavedNotification(DateTime.now(), notification, isSuccess))
            .async()
            .subscribe({}, {
                Log.d("qwerty", it.message)
            })
            .keep()
    }
}