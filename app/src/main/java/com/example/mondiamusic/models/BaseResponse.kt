package com.example.mondiamusic.models

import okhttp3.Headers
import java.io.Serializable

interface BaseResponse {
    var headers: Headers
    val UnAuthorizedRequest: Boolean
    val Success: Boolean
    val Sequence: String
    val Error: Error
    val Message: String}

data class Error(
    val Type: String? = null,
    val Code: String? = null,
    val Message: String? = null,
    val Details: String? = null,
    val ValidationErrors: List<ValidationErrors>? = null
) : Serializable

data class ValidationErrors(val Message: String? = null, val Member: String? = null) :
    Serializable