package com.zoe.wan.android.http

object ApiAddress {

    const val Account_List = "api/accounts"
    const val Account_Delete = "api/accounts/del"
    const val Account_Delete_Single = "api/account/del/"
    const val Account_Pay = "api/pay/"
    const val Account_Verify = "api/verify/"
    //首页文章
    fun getArticleList(pageCount: Int): String {
        return "article/list/$pageCount/json"
    }
}
