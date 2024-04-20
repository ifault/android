package com.zoe.wan.android.http

object BaseUrlConstants {
    private const val baseUrl: String = "http://192.168.3.82:5555/"

    fun getHost(): String {
        return baseUrl
    }
}
