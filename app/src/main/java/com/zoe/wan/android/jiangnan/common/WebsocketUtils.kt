import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPUtils
import com.zoe.wan.android.jiangnan.common.WebSocketListenerCallback
import com.zoe.wan.android.http.Constants.SP_SETTINGS_ACCESS_TOKEN
import okhttp3.*
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class WebsocketUtils {
    private var webSocket: WebSocket? = null
    private var callback: WebSocketListenerCallback? = null
    private var isWebSocketClosed = false

    fun setWebSocketListenerCallback(callback: WebSocketListenerCallback) {
        this.callback = callback
    }

    suspend fun startWebSocket(url: String) {
        val token = SPUtils.getInstance().getString(SP_SETTINGS_ACCESS_TOKEN)
        LogUtils.d("$url?token=$token")
        val request = Request.Builder()
            .url("$url?token=$token")
            .build()
        val listener = CustomWebSocketListener()
        val client = OkHttpClient()
        webSocket = client.newWebSocket(request, listener)
    }

    suspend fun closeWebSocket() {
        if (!isWebSocketClosed && webSocket != null) {
            isWebSocketClosed = true
            webSocket?.close(1000, "Closed by client")
            webSocket = null
            callback?.onWebSocketDisconnected()
        }
    }

    suspend fun sendWebSocketMessage(message: String) {
        webSocket?.send(message)
    }

    private inner class CustomWebSocketListener : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            callback?.onWebSocketConnected()
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            callback?.onWebSocketError()
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            callback?.onWebSocketMessage(text)
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            callback?.onWebSocketDisconnected()
        }
    }
}