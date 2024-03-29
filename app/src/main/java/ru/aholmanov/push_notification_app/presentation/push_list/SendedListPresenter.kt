package ru.aholmanov.push_notification_app.presentation.push_list

import com.arellomobile.mvp.InjectViewState
import ru.aholmanov.push_notification_app.extension.async
import ru.aholmanov.push_notification_app.mvp.BasePresenter
import ru.aholmanov.push_notification_app.dataLayer.storage.PushNotificationRepository
import javax.inject.Inject

@InjectViewState
class SendedListPresenter @Inject constructor(
    private val repository: PushNotificationRepository
) : BasePresenter<SendedListView>() {

    fun observeNotification(){
        repository.observeNotifications()
            .async()
            .subscribe ({
                viewState.showNotifications(it)
            },{
                viewState.showError(it)
            })
            .keep()
    }
}