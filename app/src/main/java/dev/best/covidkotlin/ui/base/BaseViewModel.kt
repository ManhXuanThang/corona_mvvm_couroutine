package dev.best.covidkotlin.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.best.covidkotlin.data.remote.toBaseException
import dev.best.covidkotlin.utils.SingleLiveEvent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseViewModel : ViewModel() {
    //loading flag
    val isLoading by lazy { MutableLiveData<Boolean>(false) }

    //error message
    val errorMessage by lazy { SingleLiveEvent<String>() }

    //optional flags
    val noInternetConnectionEvent by lazy { SingleLiveEvent<Unit>() }
    val connectTimeoutEvent by lazy { SingleLiveEvent<Unit>() }
    val forceUpdateAppEvent by lazy { SingleLiveEvent<Unit>() }
    val serverMaintainEvent by lazy { SingleLiveEvent<Unit>() }
    val unknowErrorEvent by lazy { SingleLiveEvent<Unit>() }


    //exception handler for couroutine
    private val exceptionHandler by lazy {
        CoroutineExceptionHandler { context, throwable ->
            viewModelScope.launch {
                onError(throwable)
            }
        }
    }

    open suspend fun onError(throwable: Throwable) {
        Timber.e("onError: "+throwable.localizedMessage)
        withContext(Dispatchers.Main) {
            when (throwable) {
                //case no internet connection
                is UnknownHostException -> {
                    noInternetConnectionEvent.call()
                }
                is ConnectException -> {
                    noInternetConnectionEvent.call()
                }

                //case request time out
                is SocketTimeoutException -> {
                    connectTimeoutEvent.call()
                }
                else -> {
                    //convert throwable to base exception to get error infomation
                    val baseException = throwable.toBaseException()
                    when (baseException.httpCode) {
                        HttpURLConnection.HTTP_UNAUTHORIZED -> {
                            errorMessage.value = baseException.message
                        }
                        HttpURLConnection.HTTP_INTERNAL_ERROR -> {
                            errorMessage.value = baseException.message
                        }
                        else -> {
                            unknowErrorEvent.call()
                        }
                    }
                }
            }
            hideLoading()
        }
    }

    open fun showError(e: Throwable) {
        errorMessage.value = e.message
    }

    fun showLoading() {
        isLoading.value = true
    }

    fun hideLoading() {
        isLoading.value = false
    }
}