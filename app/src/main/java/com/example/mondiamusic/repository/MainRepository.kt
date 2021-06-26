package com.example.mondiamusic.repository

import com.example.mondiamusic.models.TokenResponse
import com.example.mondiamusic.network.networkCall.NetworkCall
import com.example.mondiamusic.network.retrofit.NetworkUtils
import com.example.mondiamusic.network.retrofit.ServiceFactory
import com.example.mondiamusic.network.service.ApiService

/** MainRepository class is responsible for API Calls or database operations if there any*/

object MainRepository {

    private const val TAG = "MainRepository"

    fun getNewToken() = NetworkCall.makeCall(TokenResponse::class.java) {
        ServiceFactory.create(ApiService::class.java).getToken()
    }

    fun searchForMusic(query: String) = NetworkCall.makeCall(ArrayList::class.java) {
        ServiceFactory.create(ApiService::class.java).searchSongs(query, NetworkUtils.DATA_LIMIT)
    }

}