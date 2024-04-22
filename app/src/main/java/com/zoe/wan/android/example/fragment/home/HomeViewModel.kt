package com.zoe.wan.android.fragment.home

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.ToastUtils
import com.youth.banner.util.LogUtils
import com.zoe.wan.android.example.repository.Repository
import com.zoe.wan.android.example.repository.data.HomeListData
import com.zoe.wan.android.example.repository.data.HomeListItemData
import com.zoe.wan.base.BaseViewModel
import com.zoe.wan.base.SingleLiveEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : BaseViewModel(application) {

    var homeListData = SingleLiveEvent<List<HomeListItemData?>?>()
    var isMonitrong = SingleLiveEvent<Boolean>().apply {
        value = false
    }
    init {
        getHomeList()
    }

    fun initData(){
        getHomeList()
    }

//    private val refreshIntervalMillis = 10 * 1000L
    private fun getHomeList() {
        launch({
            var data: HomeListData? = Repository.getHomeList()
            var list = data?.items ?: emptyList()
            homeListData.postValue(list)
        }, onError = {
            homeListData.postValue(emptyList())
        }, onComplete = {
        })
    }

    fun pay(uuid: String, callback: (state: Boolean) -> Unit) {
        launch({
            var data  = Repository.pay(uuid)
        }, onComplete = {
            callback.invoke(true)
            ToastUtils.showLong("支付完毕")
        })
    }

    fun startMonitor(){
        isMonitrong.postValue(!isMonitrong.value!!)
    }
}
