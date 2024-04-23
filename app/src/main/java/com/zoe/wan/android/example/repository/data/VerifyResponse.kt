package com.zoe.wan.android.example.repository.data

data class VerifyResponse(
    val accessToken: String,
    val refreshToken: String,
    val tokenType: String
)
