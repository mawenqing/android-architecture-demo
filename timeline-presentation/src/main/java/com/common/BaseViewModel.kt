package com.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

/**
 * @author mawenqing.
 */
abstract class BaseViewModel(private val mainDispatcher: CoroutineDispatcher) : ViewModel() {
    private val errorMessage = MutableLiveData<String>()

    val error: LiveData<String> = errorMessage

    open fun handleError(e: Throwable) {
        errorMessage.postValue(e.message)
    }

    fun uiJob(block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch(mainDispatcher) {
            block()
        }
    }
}
