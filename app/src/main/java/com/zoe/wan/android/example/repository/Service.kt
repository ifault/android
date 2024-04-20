package com.zoe.wan.android.example.repository

import com.zoe.wan.android.http.BaseResponse
import retrofit2.http.GET

interface Service {

    @GET("/api/accounts")
    suspend fun accountList(): BaseResponse<AccountList>?

}