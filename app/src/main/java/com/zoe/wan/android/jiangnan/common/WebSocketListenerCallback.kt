package com.zoe.wan.android.jiangnan.common

interface WebSocketListenerCallback {
    fun onWebSocketConnected()
    fun onWebSocketDisconnected()
    fun onWebSocketMessage(message: String)

    fun onWebSocketError()
}