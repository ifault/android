package com.zoe.wan.android.fragment.settting

import android.app.Application
import androidx.databinding.ObservableField
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.SPUtils
import com.google.gson.Gson
import com.zoe.wan.android.example.common.Constants.SP_SETTINGS
import com.zoe.wan.android.example.repository.data.SettingData
import com.zoe.wan.base.BaseViewModel

class SettingViewModel(application: Application) : BaseViewModel(application) {
    val server = ObservableField<String>()
    val token = ObservableField<String>()
    val password = ObservableField<String>()
    val accounts = ObservableField<String>()


    init {
        val setting = SPUtils.getInstance().getString("settings")
        val savedUser = GsonUtils.fromJson(setting, SettingData::class.java)
        server.set(savedUser?.server)
        token.set(savedUser?.token)
        password.set(savedUser?.password)

        SPUtils.getInstance().put(SP_SETTINGS, GsonUtils.toJson(savedUser))
    }
}
