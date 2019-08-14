package ru.aholmanov.push_notification_app.presentation.push_sender

import com.arellomobile.mvp.MvpView

interface SenderView:MvpView {
    fun showLoading(show: Boolean)

    fun showError(message: String)

    fun onInternetStateChanged(connected: Boolean)

    fun showSuccess()
}