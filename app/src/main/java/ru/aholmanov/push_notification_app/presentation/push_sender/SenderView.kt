package ru.aholmanov.push_notification_app.presentation.push_sender

import com.arellomobile.mvp.MvpView

interface SenderView:MvpView {
    fun showLoading(show: Boolean)

    fun onError(error: Throwable)

    fun onInternetStateChanged(connected: Boolean)

    fun showSuccess()
}