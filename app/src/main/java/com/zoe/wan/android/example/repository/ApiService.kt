package com.zoe.wan.android.example.repository

import com.zoe.wan.android.example.repository.data.HomeListData
import com.zoe.wan.android.http.ApiAddress
import com.zoe.wan.android.http.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
//    suspend fun homeList(): BaseResponse<HomeListData>?
//    @GET(ApiAddress.Article_List + "{pageCount}/json")
//    suspend fun homeList(@Path("pageCount") pageCount: String): BaseResponse<HomeListData>?

    @GET(ApiAddress.Account_List)
    suspend fun homeList(): BaseResponse<HomeListData>?
}


