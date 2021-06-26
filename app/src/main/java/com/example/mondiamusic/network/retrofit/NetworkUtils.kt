package com.example.mondiamusic.network.retrofit

object NetworkUtils {


    const val development_Environment = "http://staging-gateway.mondiamedia.com/"
    const val gateway_key = "Ge6c853cf-5593-a196-efdb-e3fd7b881eca"
    const val BASE_URL: String = development_Environment
    const val DATA_LIMIT: Int = 100

    enum class NetworkResponseCodes(val code: Int) {
        UnAuthorizedUser(401),
        SUCCESS(0),
        ERROR(-1)
    }


}