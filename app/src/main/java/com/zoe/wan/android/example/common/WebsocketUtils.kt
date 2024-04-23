import com.zoe.wan.android.example.common.WebSocketListenerCallback
import kotlinx.coroutines.*
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
        val request = Request.Builder().url(url).build()
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
            callback?.onWebSocketDisconnected()
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            callback?.onWebSocketMessage(text)
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            callback?.onWebSocketDisconnected()
        }
    }
}