package com.example.mondiamusic.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mondiamusic.R
import com.example.mondiamusic.callback.OnItemClick
import com.example.mondiamusic.databinding.SongItemRvBinding
import com.example.mondiamusic.models.Song
import kotlin.collections.ArrayList

class SongsAdapter(list: ArrayList<Song>, private val listener: OnItemClick) :
    RecyclerView.Adapter<SongsAdapter.MyViewHolder>() {

    private var data = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.song_item_rv, parent, false)
        val binding = SongItemRvBinding.bind(itemView)
        return MyViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) =
        holder.bind(data[position])

    override fun getItemCount(): Int = data.size

    fun updateData(list: ArrayList<Song>) {
        this.data = list
        notifyDataSetChanged()
    }

    inner class MyViewHolder(
        private val binding: SongItemRvBinding,
        private val listener: OnItemClick
    ) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("ClickableViewAccessibility")
        fun bind(item: Song) {
            try {
                binding.title.text = item.title
                val s = "${item.mainArtist.name} - ${item.type}"
                binding.artist.text = s
                Glide.with(binding.songImage).load("http:${item.cover.medium}").error(R.drawable.error_image_2)
                    .into(binding.songImage)
                binding.container.setOnClickListener { listener.onItemClicked(adapterPosition) }

                binding.root.setOnTouchListener { _, event ->
                    when (event?.action) {
                        MotionEvent.ACTION_DOWN -> {
                            binding.root.scaleX = 0.9F
                            binding.root.scaleY = 0.9F
                        }
                        MotionEvent.ACTION_UP -> {
                            binding.root.scaleX = 1F
                            binding.root.scaleY = 1F
                        }
                        MotionEvent.ACTION_CANCEL -> {
                            binding.root.scaleX = 1F
                            binding.root.scaleY = 1F
                        }
                    }
                    false
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}