package vn.toanpv.sample.arena.core.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

sealed class RetrieveDataState<out R> {
    object Start : RetrieveDataState<Nothing>()
    data class Success<out T>(val data: T) : RetrieveDataState<T>()
    data class Error(val throwable: Exception) : RetrieveDataState<Nothing>()
}

fun <T> retrieveData(
    context: CoroutineContext = Dispatchers.IO, block: suspend () -> T
): LiveData<RetrieveDataState<T>> = liveData(context) {
    emit(RetrieveDataState.Start)
    try {
        emit(RetrieveDataState.Success(data = block.invoke()))
    } catch (e: Exception) {
        emit(RetrieveDataState.Error(e))
    }
}

suspend fun <T> MutableLiveData<RetrieveDataState<T>>.retrieveData(
    block: suspend () -> T
) {
    postValue(RetrieveDataState.Start)
    try {
        postValue(RetrieveDataState.Success(data = block.invoke()))
    } catch (e: Exception) {
        postValue(RetrieveDataState.Error(e))
    }
}

fun <T> RetrieveDataState<T>.onSuccess(callback: (T) -> Unit): RetrieveDataState<T> {
    if (this is RetrieveDataState.Success) {
        callback.invoke(this.data)
    }
    return this
}

fun <T> RetrieveDataState<T>.onStart(callback: () -> Unit): RetrieveDataState<T> {
    if (this is RetrieveDataState.Start) {
        callback.invoke()
    }
    return this
}

fun <T> RetrieveDataState<T>.onError(callback: (Throwable) -> Unit): RetrieveDataState<T> {
    if (this is RetrieveDataState.Error) {
        callback.invoke(this.throwable)
    }
    return this
}