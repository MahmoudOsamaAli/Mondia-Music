package com.example.mondiamusic.models

import okhttp3.Headers

data class BaseResponse2<T>(
    var headers: Headers? = null,
    var UnAuthorizedRequest: Boolean? = null,
    var Success: Boolean? = null,
    var Sequence: String? = null,
    var Error: Error? = null,
    var Message: String? = null,
)
