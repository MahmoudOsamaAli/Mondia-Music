package com.example.mondiamusic.models

import okhttp3.Headers

data class TokenResponse(
    val expiresIn: String = "",
    val accessToken: String = "",
    val tokenType: String = "",
    override var headers: Headers,
    override val UnAuthorizedRequest: Boolean,
    override val Success: Boolean,
    override val Sequence: String,
    override val Error: Error,
    override val Message: String
):BaseResponse