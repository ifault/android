package com.zoe.wan.android.http

object BaseUrlConstants {
    private const val baseUrl: String = "http://192.168.3.194:1234/"
//    private const val baseUrl: String = "https://www.wanandroid.com/"

    fun getHost(): String {
        return baseUrl
    }
}
