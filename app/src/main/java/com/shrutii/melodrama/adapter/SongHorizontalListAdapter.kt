package com.shrutii.melodrama.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shrutii.melodrama.databinding.ActivityCategoryScreenBinding
import com.shrutii.melodrama.databinding.SongItemBinding
import com.shrutii.melodrama.models.SongModel

class SongHorizontalListAdapter(val context: Context,val list:ArrayList<SongModel>,val onClickSong:(songModel:SongModel)->Unit):
    RecyclerView.Adapter<SongHorizontalListAdapter.SongHorizontalListVH>() {
        inner class SongHorizontalListVH(val binding: SongItemBinding): RecyclerView.ViewHolder(binding.root){
            fun bind(pos:Int){
            Glide.with(context).load(list[pos].Image).into(binding.songImage)
                binding.songName.text=list[pos].Song
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongHorizontalListVH =SongHorizontalListVH(SongItemBinding.inflate(
        LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: SongHorizontalListVH, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = list.size

}