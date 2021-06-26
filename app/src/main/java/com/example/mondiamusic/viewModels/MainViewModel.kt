package com.example.mondiamusic.viewModels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.mondiamusic.models.Song
import com.example.mondiamusic.models.TokenResponse
import com.example.mondiamusic.repository.MainRepository
import com.example.mondiamusic.utils.TokenUtil

class MainViewModel(application: Application) : AndroidViewModel(application) {

    var songsList:MutableLiveData<ArrayList<Song>> = MutableLiveData()
    var selectedSong:MutableLiveData<Song> = MutableLiveData()

    private fun getBaseContext(): Context {
        return getApplication<Application>().baseContext
    }

    fun checkForToken(): Boolean {
        TokenUtil.loadTokenToMemory(getBaseContext())
        return TokenUtil.getTokenFromMemory().isEmpty()
    }

    fun getNewToken() = MainRepository.getNewToken()

    fun saveTokeData(it: TokenResponse) = TokenUtil.saveToken(getBaseContext(), it.accessToken,it.expiresIn.toInt())

    fun getSongs(query:String) = MainRepository.searchForMusic(query)

    fun setSelectedItem(position: Int) {
        selectedSong.value = songsList.value?.get(position)
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}