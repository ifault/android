package com.zoe.wan.android.http

import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPUtils
import com.zoe.wan.android.http.Constants.SP_SETTINGS_ACCESS_TOKEN
import com.zoe.wan.android.http.Constants.SP_SETTINGS_REFRESH_TOKEN
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = SPUtils.getInstance().getString(SP_SETTINGS_ACCESS_TOKEN)
        val refresh = SPUtils.getInstance().getString(SP_SETTINGS_REFRESH_TOKEN)
        LogUtils.d(token)
        LogUtils.d(refresh)
        val request = chain.request().newBuilder().addHeader("Authorization", "Bearer $token")
            .addHeader("Refresh-Token", refresh).build()
        return chain.proceed(request)
    }
}