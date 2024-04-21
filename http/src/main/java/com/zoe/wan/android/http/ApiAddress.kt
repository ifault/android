package com.zoe.wan.android.http

object ApiAddress {

    const val Account_List = "api/accounts/"
    //首页文章
    const val Article_List = "article/list/"
    fun getArticleList(pageCount: Int): String {
        return "article/list/$pageCount/json"
    }
}
