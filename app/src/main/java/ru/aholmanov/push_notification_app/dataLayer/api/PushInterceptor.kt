package ru.aholmanov.push_notification_app.dataLayer.api

import okhttp3.Interceptor
import okhttp3.Response
import ru.aholmanov.push_notification_app.BuildConfig

class PushInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val newUrl = original.url().newBuilder()
            .addQueryParameter("token", BuildConfig.API_KEY)
            .build()
        val request = original.newBuilder()
            .url(newUrl)
            .build()
        return chain.proceed(request)
    }
}