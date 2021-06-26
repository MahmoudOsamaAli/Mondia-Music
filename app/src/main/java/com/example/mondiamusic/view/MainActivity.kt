package com.example.mondiamusic.view

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager

import com.example.mondiamusic.R
import com.example.mondiamusic.adapter.SongsAdapter
import com.example.mondiamusic.callback.OnItemClick
import com.example.mondiamusic.databinding.ActivityMainBinding
import com.example.mondiamusic.network.networkCall.ServerCallBack
import com.example.mondiamusic.viewModels.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), OnItemClick {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: SongsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        init()
    }

    private fun init() {
        validateAPIToke()
        /** Getting a default data to populate th UI util user searches his favorite songs or albums */
        getSongsFromServer("taylor")
        setUpObserver()
    }

    /** Api Token validation for it's expires date or if there any using TokenUtil class */
    private fun validateAPIToke() {
        if (viewModel.checkForToken()) {
            viewModel.getNewToken().observe(this, {
                if (it.status == ServerCallBack.Status.SUCCESS) {
                    viewModel.saveTokeData(it.data!!)
                } else if (it.status == ServerCallBack.Status.ERROR) {
                    showSnackBar(it.message ?: "")
                }
            })
        }
    }

    private fun getSongsFromServer(query: String) {
        viewModel.getSongs(query).observe(this, {
            when (it.status) {
                ServerCallBack.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    if (!it.data.isNullOrEmpty()) viewModel.songsList.value = it.data
                    else showSnackBar(resources.getString(R.string.no_songs_found))
                }
                ServerCallBack.Status.ERROR -> {
                    showSnackBar(it.message ?: "")
                    binding.progressBar.visibility = View.GONE
                }
                else -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun setUpObserver() {
        viewModel.songsList.observe(this, {
            if (!this::adapter.isInitialized) {
                adapter = SongsAdapter(it, this)
                binding.songsRv.layoutManager = GridLayoutManager(this, 2)
                binding.songsRv.adapter = adapter
                binding.animator.displayedChild = 1
            } else adapter.updateData(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_activity_menu_search, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = (menu.findItem(R.id.search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }
        searchView.queryHint = resources.getString(R.string.search_query_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) getSongsFromServer(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean = false

        })
        return true
    }

    override fun onItemClicked(position: Int) {
        try {
            viewModel.setSelectedItem(position)
            /** show item details bottom sheet dialog fragment*/
            val dialog = SongDetailsDialog()
            dialog.show(supportFragmentManager, dialog.tag)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun showSnackBar(message: String) = Snackbar.make(
        findViewById(android.R.id.content),
        message as CharSequence,
        Snackbar.LENGTH_LONG
    ).show()

}