package com.example.mondiamusic.view

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.mondiamusic.R
import com.example.mondiamusic.databinding.SongDetailsFragmnentBinding
import com.example.mondiamusic.extensions.ExtensionsUtils.duration
import com.example.mondiamusic.extensions.ExtensionsUtils.setupFullHeight
import com.example.mondiamusic.models.Song
import com.example.mondiamusic.viewModels.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SongDetailsDialog : BottomSheetDialogFragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: SongDetailsFragmnentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.song_details_fragmnent, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.selectedSong.observe(viewLifecycleOwner, { updateUI(it) })
    }

    private fun updateUI(item: Song?) {
        try {
            if (item != null) {
                val url = "http:${item.cover.medium}"
                Glide.with(binding.songImage).load(url).error(R.drawable.error_image_2)
                    .into(binding.songImage)
                Glide.with(binding.backgoundImage).load(url).error(R.drawable.error_image_2)
                    .into(binding.backgoundImage)
                binding.title.text = item.title
                binding.type.text = item.type
                binding.artist.text = item.mainArtist.name
                binding.date.text = item.publishingDate.split("T")[0]
                binding.duration.text = duration(item.duration)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setupFullHeight(requireContext())
        return dialog
    }
}