package com.example.mondiamusic.utils

import android.content.Context
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

object TokenUtil {

    private var token: String = ""
    private const val TAG = "TokenUtil"
    fun loadTokenToMemory(context: Context) {
        Log.i(TAG, "loadTokenToMemory: ${getToken(context)}")
        if (isTokenExpired(context)) token = getToken(context)
        else removeToken(context)
    }

    @Suppress("DEPRECATION")
    private fun isTokenExpired(context: Context): Boolean =
        SharedPreferencesManager.getInstance(context)
            ?.getStringPref(Constants.EXPIRE_TIME, "").let {
                if (it != null && it.isNotEmpty()){
                    Log.i(TAG, "isTokenExpired: ${Date().before(Date(it))}")
                    Date().before(Date(it))
                }
                else false
            }

    fun getTokenFromMemory(): String = token

    fun getToken(context: Context): String =
        SharedPreferencesManager.getInstance(context)?.getStringPref(Constants.TOKEN, "")
            .orEmpty()


    fun saveToken(context: Context, token: String?, expiresIn: Int?) {
        val sdf = SimpleDateFormat(Constants.DATE_FORMAT, Locale.ROOT)
        val calendar =
            Calendar.getInstance(Locale.ROOT).apply { add(Calendar.SECOND, expiresIn ?: 0) }
        SharedPreferencesManager.getInstance(context)?.apply {
            savePrefSting(Constants.TOKEN, token)
            savePrefSting(Constants.EXPIRE_TIME, sdf.format(calendar.time))
        }
        loadTokenToMemory(context)
    }

    private fun removeToken(context: Context) {
        Log.i(TAG, "removeToken: ")
        SharedPreferencesManager.getInstance(context)?.clearKey(Constants.TOKEN)
        token = "" // remove token from memory
    }
}