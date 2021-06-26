package com.example.mondiamusic.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager


class SharedPreferencesManager private constructor(context: Context) {

    fun savePrefSting(key: String?, value: String?): Boolean {
        val editor = prefs!!.edit()
        editor.putString(key, value)
        return editor.commit()
    }

    fun savePrefBoolean(key: String?, value: Boolean): Boolean {
        val editor = prefs!!.edit()
        editor.putBoolean(key, value)
        return editor.commit()
    }

    fun getStringPref(key: String, defaultValue: String): String {
        return prefs?.getString(key, defaultValue) ?: defaultValue
    }

    fun getBooleanPref(key: String?, defaultValue: Boolean): Boolean {
        return prefs!!.getBoolean(key, defaultValue)
    }

    fun clearKey(key: String?): Boolean {
        val editor = prefs!!.edit()
        editor.remove(key)
        return editor.commit()
    }

    companion object {
        private var prefs: SharedPreferences? = null
        private var instance: SharedPreferencesManager? = null

        //***************************get Shared Perf Instance*************************************
        fun getInstance(context: Context): SharedPreferencesManager? {
            if (instance == null) {
                instance = SharedPreferencesManager(context)
            }
            return instance
        }
    }

    init {
        if (prefs == null) {
            prefs =
                PreferenceManager.getDefaultSharedPreferences(context)
        }
    }
}
