package com.example.mondiamusic.network.retrofit

import android.util.Log
import com.example.mondiamusic.utils.TokenUtil
import okhttp3.Interceptor
import okhttp3.Response

/** ClientInterceptor is a class that we can cast out headers when we deal with server API Calls
 *
 * Here we set API headers as described in Swagger application */

class ClientInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        val request = request()
            .newBuilder()
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
            .addHeader("Accept", "application/json")
            .addHeader("X-MM-GATEWAY-KEY", NetworkUtils.gateway_key)
        //add token key value if exist
        Log.i("ClientInterceptor", "intercept: ${TokenUtil.getTokenFromMemory()}")
        if (TokenUtil.getTokenFromMemory().isNotEmpty()) {
            request.addHeader("Authorization", "Bearer ${TokenUtil.getTokenFromMemory()}")
        }
        proceed(request.build())
    }


}