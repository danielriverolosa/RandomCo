package com.rivero.daniel.randomco.domain.usecase

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import timber.log.Timber


abstract class UseCase<out T> {
    private var callback: UseCaseCallback<T>? = null

    fun executeAsync(callback: UseCaseCallback<T>, vararg params: Any) {
        this.callback = callback
        val deferred = async(CommonPool) { runInBackground(*params) }
        launch(UI) { manageDeferred(deferred) }
    }

    private suspend fun manageDeferred(deferred: Deferred<T>) {
        try {
            notifySuccess(deferred.await())
        } catch (e: Exception) {
            notifyError(e)
        }
    }

    protected abstract fun runInBackground(vararg params: Any): T

    private fun notifySuccess(data: T) {
        callback?.let {
            it.onSuccess(data)
            Timber.d("""Success: ${data.toString()}""")
        } ?: throw IllegalStateException("The callback is null!!!")
    }

    private fun notifyError(e: Throwable) {
        callback?.let {
            it.onError(e)
            Timber.d("""Fail: $e""")
        } ?: throw IllegalStateException("The callback is null!!!")
    }

}