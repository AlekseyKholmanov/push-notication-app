package ru.aholmanov.push_notification_app.api

import okhttp3.Interceptor
import okhttp3.Response
import ru.aholmanov.push_notification_app.BuildConfig

class PushInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val origianl = chain.request()
        val newUrl = origianl.url().newBuilder()
            .addQueryParameter("token", BuildConfig.API_KEY)
            .build()
        val request = origianl.newBuilder()
            .url(newUrl)
            .build()
        return chain.proceed(request)
    }
}