package com.shrutii.melodrama.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shrutii.melodrama.R
import com.shrutii.melodrama.databinding.ActivityCategoryScreenBinding
import com.shrutii.melodrama.databinding.SongItemBinding
import com.shrutii.melodrama.models.SongModel

class SongHorizontalListAdapter(
    val context: Context,
    val list: ArrayList<SongModel>,
    val onClickSong: (songModel: SongModel) -> Unit
) :
    RecyclerView.Adapter<SongHorizontalListAdapter.SongHorizontalListVH>() {
    inner class SongHorizontalListVH(val binding: SongItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {
            val temp = list[pos].Image?.split("/d/")?.get(1) ?: ""
            val temp2 = temp.split("/")[0]
            val img = "https://drive.google.com/uc?id=$temp2"
            Glide.with(context).load(img).placeholder(R.drawable.place_holder).centerCrop().into(binding.songImage)
            binding.songName.text = list[pos].Song
            binding.artistName.text = list[pos].Artist
            binding.songCard.setOnClickListener {
                onClickSong(list[pos])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongHorizontalListVH =
        SongHorizontalListVH(
            SongItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: SongHorizontalListVH, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = list.size

}