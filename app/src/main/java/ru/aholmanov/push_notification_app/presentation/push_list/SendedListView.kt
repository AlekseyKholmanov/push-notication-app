package ru.aholmanov.push_notification_app.presentation.push_list

import com.arellomobile.mvp.MvpView
import ru.aholmanov.push_notification_app.model.SavedNotification


interface SendedListView : MvpView {
    fun showError(t: Throwable)

    fun showNotifications(notifications: List<SavedNotification>)
}