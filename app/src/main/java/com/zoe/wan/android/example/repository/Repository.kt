package com.zoe.wan.android.example.repository

import com.blankj.utilcode.util.ToastUtils
import com.zoe.wan.android.example.repository.data.AccountRequest
import com.zoe.wan.android.example.repository.data.HomeListData
import com.zoe.wan.android.http.BaseResponse
import com.zoe.wan.android.http.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


object Repository {
    private const val Success_Code = 0
    private const val Need_login_Code = -1001

    suspend fun getHomeList(): HomeListData? {
        val data: BaseResponse<HomeListData>? = getDefaultApi().homeList()
        return responseCall(data)
    }

    suspend fun clearAccount(): Boolean {
        val data: BaseResponse<Any>? = getDefaultApi().clearAccount()
        return responseNoDataCall(data)
    }
    suspend fun addAccount(username: String, password: String): Boolean {
        val data: BaseResponse<Any>? = getDefaultApi().addAccount(AccountRequest(username, password))
        return responseNoDataCall(data)
    }

    private fun responseNoDataCall(
        response: BaseResponse<Any>?,
        needLogin: (() -> Unit?)? = null
    ): Boolean {
        if (response == null) {
            GlobalScope.launch(Dispatchers.Main) {
                ToastUtils.showShort("请求异常")
            }
            return false
        }

        if (response.getErrCode() == Success_Code) {
            return true
        } else if (response.getErrCode() == Need_login_Code) {
            GlobalScope.launch(Dispatchers.Main) {
                ToastUtils.showShort(response.getErrMsg() ?: "")
            }
            needLogin?.invoke()
            return false
        } else {
            GlobalScope.launch(Dispatchers.Main) {
                ToastUtils.showShort(response.getErrMsg() ?: "")
            }
            return false
        }
    }

    /**
     * 返回值处理
     */
    private fun <T> responseCall(
        response: BaseResponse<T>?,
        needLogin: (() -> Unit?)? = null
    ): T? {
        if (response == null) {
            GlobalScope.launch(Dispatchers.Main) {
                ToastUtils.showShort("请求异常")
            }
            return null
        }

        if (response.getErrCode() == Success_Code) {
            return response.getData()
        } else if (response.getErrCode() == Need_login_Code) {
            GlobalScope.launch(Dispatchers.Main) {
                ToastUtils.showShort(response.getErrMsg() ?: "")
            }
            needLogin?.invoke()
            return null
        } else {
            GlobalScope.launch(Dispatchers.Main) {
                ToastUtils.showShort(response.getErrMsg() ?: "")
            }
            return null
        }
    }

    private fun getDefaultApi(): ApiService {
        return RetrofitClient.getInstance().getDefault(ApiService::class.java)
    }

}
