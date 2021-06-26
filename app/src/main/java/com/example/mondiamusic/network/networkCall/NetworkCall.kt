package com.example.mondiamusic.network.networkCall

import androidx.lifecycle.MutableLiveData
import com.example.mondiamusic.network.retrofit.NetworkUtils
import com.example.mondiamusic.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.lang.reflect.Type
import java.net.ConnectException
import java.net.UnknownHostException

/** NetworkCall object class is responsible for handling server callbacks
 * and serve it with an appropriate data to display it in UI */

object NetworkCall {

    fun <T> makeCall(
        typeConverter: Type,
        requestFun: suspend () -> Response<T>
    ): MutableLiveData<ServerCallBack<T>> {
        val result = MutableLiveData<ServerCallBack<T>>()
        //this is for showing loading on the screen
        result.value = ServerCallBack.loading(null)
        //this is for making the call inside CoroutineScope
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = requestFun()
                withContext(Dispatchers.Main) {
                    when {
                        response.body() != null -> {
                            response.body()?.let {
                                result.value = ServerCallBack.success(it)
                                return@withContext
                            }
                        }
                        response.code() == NetworkUtils.NetworkResponseCodes.UnAuthorizedUser.code -> {
                            result.value = ServerCallBack.error(Constants.UNAUTHORIZED_ERROR)

                        }
                        response.errorBody() != null -> {
                            result.value = ServerCallBack.error(Constants.GENERAL_ERROR_MESSAGE)
                        }
                        else -> {
                            result.value = ServerCallBack.error(Constants.GENERAL_ERROR_MESSAGE)
                        }
                    }
                }
            } catch (e: Throwable) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    if (e is ConnectException || e is UnknownHostException) {
                        //this is no internet exception
                        result.value = ServerCallBack.error(Constants.NO_INTERNET_MESSAGE)
                    } else
                        result.value = ServerCallBack.error(Constants.GENERAL_ERROR_MESSAGE)
                }
            }
        }
        return result
    }
}