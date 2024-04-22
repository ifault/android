package com.zoe.wan.android.http

import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPUtils

object BaseUrlConstants {
    private const val baseUrl: String = "http://10.0.2.2:1234/"
    private const val SP_SETTINGS_SERVER = "SP_SETTINGS_SERVER"

    fun getHost(): String {
        var host = SPUtils.getInstance().getString(SP_SETTINGS_SERVER)
        LogUtils.d("获取host $host")
        return SPUtils.getInstance().getString(SP_SETTINGS_SERVER)?:baseUrl
    }
}
