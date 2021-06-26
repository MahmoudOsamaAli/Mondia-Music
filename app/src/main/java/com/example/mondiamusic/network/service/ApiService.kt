package com.example.mondiamusic.network.service

import com.example.mondiamusic.models.Song
import com.example.mondiamusic.models.TokenResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/** ApiService is a class that is responsible for holding
 *  different server end-points that we work with at API Calls*/

interface ApiService {

    @POST("v0/api/gateway/token/client")
    suspend fun getToken(): Response<TokenResponse>

    @GET("v2/api/sayt/flat")
    suspend fun searchSongs(
        @Query("query") query: String,
        @Query("limit") limit: Int
    ): Response<ArrayList<Song>>
}