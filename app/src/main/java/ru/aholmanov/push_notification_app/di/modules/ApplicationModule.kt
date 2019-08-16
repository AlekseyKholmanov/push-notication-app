package ru.aholmanov.push_notification_app.di.modules

import android.content.Context
import android.net.ConnectivityManager
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import ru.aholmanov.push_notification_app.dataLayer.api.ApiServiceFactory
import ru.aholmanov.push_notification_app.dataLayer.api.PushService
import ru.aholmanov.push_notification_app.dataLayer.orm.PushDatabase
import javax.inject.Singleton

@Module
class ApplicationModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideApplicationContext(): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .create()
    }

    @Provides
    @Singleton
    fun provideConnectivityManager(context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    @Provides
    @Singleton
    fun provideNewsService(gson: Gson): PushService {
        return ApiServiceFactory.createNewsService(gson)
    }

    @Provides
    @Singleton
    fun provideDatabase(context: Context): PushDatabase {
        return Room.databaseBuilder(context, PushDatabase::class.java, "pushes.db")
            .build()
    }

}