package ru.aholmanov.push_notification_app.presentation.push_sender

import com.arellomobile.mvp.InjectViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.aholmanov.push_notification_app.di.extension.async
import ru.aholmanov.push_notification_app.model.PushNotification
import ru.aholmanov.push_notification_app.mvp.BasePresenter
import ru.aholmanov.push_notification_app.storage.PushNotificationRepository
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@InjectViewState
class SenderPresenter @Inject constructor(
    private val repository: PushNotificationRepository
): BasePresenter<SenderView>()
{
    fun sentNotification(notification: PushNotification) {
        viewState.showLoading(true)
        repository.sendNotification(notification)
            .delay(5000L, TimeUnit.MILLISECONDS)
            .async()
            .doAfterTerminate{viewState.showLoading(false)}
            .subscribe({
                viewState.showSuccess()
            }, { error ->
                viewState.onError(error)
            })
            .keep()
    }

}