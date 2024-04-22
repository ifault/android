import com.blankj.utilcode.util.ToastUtils
import com.zoe.wan.android.example.common.WebSocketListenerCallback
import okhttp3.*
import okhttp3.WebSocketListener

class WebsocketUtils {
    private var webSocket: WebSocket? = null
    private var callback: WebSocketListenerCallback? = null
    private var isWebSocketClosed = false

    fun setWebSocketListenerCallback(callback: WebSocketListenerCallback) {
        this.callback = callback
    }

    fun startWebSocket(url: String) {
        val request = Request.Builder().url(url).build()
        val listener = CustomWebSocketListener()
        webSocket = OkHttpClient().newWebSocket(request, listener)
    }
    fun closeWebSocket() {
        if (!isWebSocketClosed && webSocket != null) {
            isWebSocketClosed = true
            webSocket?.close(1000, "Closed by client")
            webSocket = null
            callback?.onWebSocketDisconnected()
        }
    }
    fun sendWebSocketMessage(message: String) {
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