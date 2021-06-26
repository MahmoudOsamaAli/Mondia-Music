package com.example.mondiamusic.network.retrofit

/** This is a factory class that creates an ApiService objects that is ready to call an Api*/

class ServiceFactory {

    companion object {

        @Synchronized
        fun <T> create(service: Class<T>): T {
            return RetrofitObject.getInstance()?.create(service)!!
        }
    }
}