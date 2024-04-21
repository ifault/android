package com.zoe.wan.android.http

import com.blankj.utilcode.util.SPUtils
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = SPUtils.getInstance().getString("token")
        val request = chain.request().newBuilder().addHeader("token", token).build()
        return chain.proceed(request)
    }
}