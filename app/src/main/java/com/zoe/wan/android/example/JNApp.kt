package com.zoe.wan.android.example

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.zoe.wan.android.http.RetrofitClient
import com.zoe.wan.base.loading.LoadingUtils

class JNApp:Application() {
    override fun onCreate() {
        super.onCreate()
        RetrofitClient.getInstance().setContext(applicationContext)
        LoadingUtils.init(this)
    }
}
