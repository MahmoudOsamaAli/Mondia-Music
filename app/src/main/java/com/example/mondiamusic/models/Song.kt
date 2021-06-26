package com.example.mondiamusic.models

import okhttp3.Headers

data class Song(
    val volumeNumber: Int = 0,
    val mainArtist: MainArtist,
    val trackNumber: Int = 0,
    val release: Release,
    val type: String = "",
    val title: String = "",
    val publishingDate: String = "",
    val duration: Int = 0,
    val cover: Cover,
    val additionalArtists: List<AdditionalArtistsItem>?,
    val adfunded: Boolean = false,
    val streamable: Boolean = false,
    val genres: List<String>?,
    val extendedMetaData: ExtendedMetaData,
    val bundleOnly: Boolean = false,
    val id: Int = 0,
    val idBag: IdBag,
    val statistics: Statistics,
    override var headers: Headers,
    override val UnAuthorizedRequest: Boolean,
    override val Success: Boolean,
    override val Sequence: String,
    override val Error: Error,
    override val Message: String
):BaseResponse