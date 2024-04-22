package com.zoe.wan.android.http

object ApiAddress {

    const val Account_List = "api/accounts"
    const val Account_Delete = "api/accounts/del"
    const val Account_Pay = "api/pay/"
    //首页文章
    fun getArticleList(pageCount: Int): String {
        return "article/list/$pageCount/json"
    }
}
