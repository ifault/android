package com.zoe.wan.android.example.repository

data class AccountList (
    val status: String?,
    val list: List<AccountData>
)

data class AccountData(
    val base64Data: String?,
    val username: String,
    val password: String
)