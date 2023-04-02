package vn.toanpv.sample.arena.core.ui.fragment

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler

open class BaseViewModel : ViewModel() {
    val handler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }
}