package com.zoe.wan.android.fragment.settting

import android.app.Application
import androidx.databinding.ObservableField
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.google.gson.Gson
import com.zoe.wan.android.example.common.Constants
import com.zoe.wan.android.example.common.Constants.SP_SETTINGS
import com.zoe.wan.android.example.repository.Repository
import com.zoe.wan.android.example.repository.data.HomeListItemData
import com.zoe.wan.android.example.repository.data.SettingData
import com.zoe.wan.base.BaseViewModel
import com.zoe.wan.base.SingleLiveEvent
import com.zoe.wan.base.loading.LoadingUtils

class SettingViewModel(application: Application) : BaseViewModel(application) {
    val server = ObservableField<String>()
    val token = ObservableField<String>()
    val password = ObservableField<String>()
    val accounts = ObservableField<String>()
    var cleared = SingleLiveEvent<Boolean>()
    var added = SingleLiveEvent<Boolean>()

    init {
        val setting = SPUtils.getInstance().getString(SP_SETTINGS)
        LogUtils.d("setting is $setting")
        val savedUser = GsonUtils.fromJson(setting, SettingData::class.java)
        server.set(savedUser?.server)
        token.set(savedUser?.token)
        password.set(savedUser?.password)
    }

    fun addAccount(){
        LoadingUtils.showLoading()
        launch({
            val data = Repository.addAccount(accounts.get().toString(), password.get().toString())
            added.postValue(data)
        }, onComplete = {
            LoadingUtils.dismiss()
            if(added.value == true){
                ToastUtils.showLong("保存成功")
            }
        }, onError = {
            LoadingUtils.dismiss()
            ToastUtils.showLong("保存失败")
        })
    }
    fun clearAccount() {
        LoadingUtils.showLoading()
        launch({
            val data = Repository.clearAccount()
            cleared.postValue(data)
        }, onComplete = {
            LoadingUtils.dismiss()
            if(cleared.value == true){
                ToastUtils.showLong("删除成功")
            }
        }, onError = {
            ToastUtils.showLong("删除失败")
        })
    }
}
