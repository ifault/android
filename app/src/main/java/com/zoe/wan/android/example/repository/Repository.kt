package com.zoe.wan.android.example.repository

import com.zoe.wan.android.http.BaseResponse
import com.zoe.wan.android.http.RetrofitClient
import retrofit2.Retrofit

object Repository {
    suspend fun getAccountList(): AccountList? {
        val data: BaseResponse<AccountList>? =
            RetrofitClient.getInstance().getDefault(Service::class.java).accountList()
        if (data?.getData() != null) {
            return data.getData()
        }
        return null;
    }
}