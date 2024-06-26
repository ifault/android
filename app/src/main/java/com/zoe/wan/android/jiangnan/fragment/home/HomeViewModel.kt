package com.zoe.wan.android.fragment.home

import NotificationUtils
import WebsocketUtils
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.os.Build
import android.os.Handler
import android.os.Message
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.zoe.wan.android.jiangnan.common.Constants.SP_SETTINGS_SERVER
import com.zoe.wan.android.jiangnan.common.WebSocketListenerCallback
import com.zoe.wan.android.jiangnan.repository.Repository
import com.zoe.wan.android.jiangnan.repository.data.HomeListData
import com.zoe.wan.android.jiangnan.repository.data.HomeListItemData
import com.zoe.wan.android.jiangnan.repository.data.SocketResponse
import com.zoe.wan.base.BaseViewModel
import com.zoe.wan.base.SingleLiveEvent

class HomeViewModel(application: Application) : BaseViewModel(application),
    WebSocketListenerCallback {
    val context = application.applicationContext
    var homeListData = MutableLiveData<List<HomeListItemData?>?>()
    var isMonitoring = SingleLiveEvent<Boolean>().apply {
        value = false
    }
    private lateinit var websocketUtils: WebsocketUtils

    init {
        getHomeList()
        websocketUtils = WebsocketUtils()
        websocketUtils.setWebSocketListenerCallback(this)
    }

    fun initData() {
        getHomeList()
    }

    //    private val refreshIntervalMillis = 10 * 1000L
    private fun getHomeList() {
        launch({
            var data: HomeListData? = Repository.getHomeList()
            var list = data?.items ?: emptyList()
            if(data?.items?.isEmpty() == true){
                homeListData.postValue(emptyList())
            }else{
                homeListData.postValue(list)
            }

        }, onError = {
            ToastUtils.showLong("获取数据出错，请验证密钥")
            homeListData.postValue(emptyList())
        }, onComplete = {
        })
    }

    @SuppressLint("HandlerLeak")
    private val mHandler: Handler = object : Handler() {
        @Suppress("unused")
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            //todo 支付成功的话 执行 Repository.pay(uuid)更新远程记录
        }
    }

    fun del(uuid: String, callback: (state: Boolean) -> Unit) {
        launch({
            Repository.del(uuid)
            initData()
        }, onComplete = {
            callback.invoke(true)
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun pay(
        activity: Activity?,
        uuid: String,
        orderStr: String,
        callback: (state: Boolean) -> Unit
    ) {
        launch({

//            if(orderStr.isNotEmpty()){
//                val decodedBytes = Base64.getDecoder().decode(orderStr)
//                val decodedString = String(decodedBytes, Charset.forName("UTF-8"))
//                val payRunnable = Runnable {
//                    val alipay = PayTask(activity)
//                    val result = alipay.payV2(decodedString, true)
//                    val msg = Message()
//                    msg.what = 1
//                    msg.obj = result
//                    mHandler.sendMessage(msg)
//                }
//                val payThread = Thread(payRunnable)
//                payThread.start()
//            }

        }, onComplete = {
            callback.invoke(true)
        })
    }

    fun startMonitor() {
        if (isMonitoring.value == true) {
            ToastUtils.showLong("关闭连接")
            launch(
                block = { websocketUtils.closeWebSocket() },
                onError = { ToastUtils.showLong("服务器异常") },
                onComplete = {
                    isMonitoring.value = false
                }
            )
        } else {
            launch(
                {
                    var url: String = SPUtils.getInstance().getString(SP_SETTINGS_SERVER)
                    url = convertToWebSocketUrl(url)
                    websocketUtils.startWebSocket(url + "/ws/")
                    isMonitoring.value = false
                    ToastUtils.showLong("打开连接")
                },
                onError = {
                    ToastUtils.showLong("服务器异常")
                    isMonitoring.value = false
                },
                onComplete = {}
            )
        }
    }


    fun convertToWebSocketUrl(url: String): String {
        val httpPrefix = "http://"
        val httpsPrefix = "https://"
        val wsPrefix = "ws://"

        return when {
            url.startsWith(httpPrefix) -> {
                val path = url.substring(httpPrefix.length)
                "$wsPrefix$path"
            }

            url.startsWith(httpsPrefix) -> {
                val path = url.substring(httpsPrefix.length)
                "$wsPrefix$path"
            }

            url.startsWith(wsPrefix) -> {
                val path = url.substring(wsPrefix.length)
                "$wsPrefix$path"
            }

            else -> url
        }
    }

    override fun onWebSocketConnected() {
        isMonitoring.postValue(true)
    }

    override fun onWebSocketDisconnected() {
        isMonitoring.postValue(false)
        ToastUtils.showLong("连接关闭")
    }
    override fun onWebSocketError() {
        isMonitoring.postValue(false)
//        ToastUtils.showLong("服务器连接异常，请检查验证信息")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onWebSocketMessage(message: String) {
        if (message != "") {
            val msg = GsonUtils.fromJson(message, SocketResponse::class.java)
            when (msg.code) {
                0 -> {
                    ToastUtils.showLong(msg.message)
                }

                -1 -> {
                    launch({ websocketUtils.closeWebSocket() })
                    ToastUtils.showLong(msg.message)
                    isMonitoring.value = false
                }

                1 -> {
                    NotificationUtils.sendNotification(
                        context,
                        "抢票软件",
                        msg.message
                    )
                }
            }

        }
    }
}
