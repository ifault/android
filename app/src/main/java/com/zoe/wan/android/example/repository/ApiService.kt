package com.zoe.wan.android.example.repository

import com.google.gson.annotations.JsonAdapter
import com.zoe.wan.android.example.repository.data.AccountRequest
import com.zoe.wan.android.example.repository.data.HomeListData
import com.zoe.wan.android.example.repository.data.VerifyResponse
import com.zoe.wan.android.http.ApiAddress
import com.zoe.wan.android.http.BaseResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
//    suspend fun homeList(): BaseResponse<HomeListData>?
//    @GET(ApiAddress.Article_List + "{pageCount}/json")
//    suspend fun homeList(@Path("pageCount") pageCount: String): BaseResponse<HomeListData>?

    @GET(ApiAddress.Account_List)
    suspend fun homeList(): BaseResponse<HomeListData>?

    @Headers("Content-Type: application/json")
    @POST(ApiAddress.Account_List)
    suspend fun addAccount(@Body accountRequest: AccountRequest) : BaseResponse<Any>?

    @POST(ApiAddress.Account_Delete)
    suspend fun clearAccount() : BaseResponse<Any>?

    @POST(ApiAddress.Account_Verify+"{token}")
    suspend fun verify(@Path("token") token: String) : BaseResponse<VerifyResponse>?

    @POST(ApiAddress.Account_Pay +"{uuid}")
    suspend fun pay(@Path("uuid") uuid: String) : BaseResponse<Any>?

    @POST(ApiAddress.Account_Delete_Single +"{uuid}")
    suspend fun del(@Path("uuid") uuid: String) : BaseResponse<Any>?

}


