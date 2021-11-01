package com.codigo.naytoe.moviedb.network

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.net.SocketTimeoutException

class AuthenticationInterceptor : Interceptor {

    private var authToken: String? = null
    private var tokenName: String? = null
    private var tokenValue: String? = null

    constructor(token: String, tokenName: String, tokenValue: String) {
        this.authToken = token
        this.tokenName = tokenName
        this.tokenValue = tokenValue
    }

    constructor(token: String) {
        this.authToken = token
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val builder = original.newBuilder()
            .header("Authorization", authToken!!)

        val request = builder.build()
        val response: Response

        try {
            response = chain.proceed(request)
            return response
        }
        catch (e: SocketTimeoutException){
            println(e.message)
        }

        return chain.proceed(request)
    }
}