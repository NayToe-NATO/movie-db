package com.codigo.naytoe.moviedb.network

import android.text.TextUtils
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RequestBuilder {

    private lateinit var retrofit: Retrofit
    private lateinit var builder: Retrofit.Builder

    fun <S> createService(serviceClass: Class<S>, baseUrl: String): S {
        return createService(serviceClass, baseUrl, "")
    }

    private fun <S> createService(serviceClass: Class<S>, baseUrl: String, authToken: String?): S {
        builder = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())

        retrofit = builder.build()

        if (!TextUtils.isEmpty(authToken)) {
            val interceptor = AuthenticationInterceptor(authToken!!)
            val client = OkHttpClient.Builder()
                .connectTimeout(180, TimeUnit.SECONDS)
                .callTimeout(180, TimeUnit.SECONDS)
                .readTimeout(180, TimeUnit.SECONDS)

            if (!client.interceptors().contains(interceptor)) {
                client.addInterceptor(interceptor)
                builder.client(client.build())
            }

            retrofit = builder.build()
        }

        return retrofit.create(serviceClass)
    }
}