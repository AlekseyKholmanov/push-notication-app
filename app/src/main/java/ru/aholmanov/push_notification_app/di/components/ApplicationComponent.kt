package ru.aholmanov.push_notification_app.di.components

import dagger.Component
import ru.aholmanov.push_notification_app.main.MainActivity
import ru.aholmanov.push_notification_app.di.modules.ApplicationModule
import ru.aholmanov.push_notification_app.presentation.push_list.SendedListPresenter
import ru.aholmanov.push_notification_app.presentation.push_sender.SenderPresenter

import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun inject(activity: MainActivity)
    fun getSenderPresenter(): SenderPresenter
    fun getSendedListPresenter(): SendedListPresenter
}