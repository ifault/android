package com.zoe.wan.android.example

import android.app.Application
import com.zoe.wan.android.http.RetrofitClient

class JNApp:Application() {
    override fun onCreate() {
        super.onCreate()
        RetrofitClient.getInstance().setContext(applicationContext)
    }
}
