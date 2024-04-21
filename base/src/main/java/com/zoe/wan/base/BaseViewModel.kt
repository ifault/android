package com.zoe.wan.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zoe.wan.http.ExceptionUtil
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.handleCoroutineException
import kotlinx.coroutines.launch

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {
    /**
     * 捕获所有异常信息
     */
    fun ViewModel.launch(
        block: suspend CoroutineScope.() -> Unit,
        onError: (e: Throwable) -> Unit = {},
        onComplete: () -> Unit = {}
    ) {
        viewModelScope.launch(CoroutineExceptionHandler { context, throwable ->
            run {
                ExceptionUtil.catchException(throwable)
                onError(throwable)
            }
        }) {
            try {
                block.invoke(this)
            } finally {
                onComplete()
            }

        }
    }
}
