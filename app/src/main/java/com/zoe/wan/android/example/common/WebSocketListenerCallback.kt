package com.zoe.wan.android.example.common

interface WebSocketListenerCallback {
    fun onWebSocketConnected()
    fun onWebSocketDisconnected()
    fun onWebSocketMessage(message: String)

    fun onWebSocketError()
}