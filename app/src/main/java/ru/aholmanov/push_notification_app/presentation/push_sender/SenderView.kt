package ru.aholmanov.push_notification_app.presentation.push_sender

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface SenderView:MvpView {
    fun showLoading(show: Boolean)

    @StateStrategyType(SkipStrategy::class)
    fun showError(message: String)

    fun onInternetStateChanged(connected: Boolean)

    @StateStrategyType(SkipStrategy::class)
    fun showSuccess()
}