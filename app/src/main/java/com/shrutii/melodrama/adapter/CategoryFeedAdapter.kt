package com.shrutii.melodrama.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shrutii.melodrama.databinding.CategoryCategorylistBinding
import com.shrutii.melodrama.databinding.CategoryTitleItemBinding
import com.shrutii.melodrama.models.SongFeedModel
import com.shrutii.melodrama.models.SongModel

class CategoryFeedAdapter(val context: Context,val list :ArrayList<SongFeedModel>,val onClickSong:(currSong:SongModel)->Unit):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val CATEGORY_TITLE=0
    private val CATEGORY_LIST=1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            CATEGORY_TITLE->CategoryTitleViewHolder(CategoryTitleItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            else ->
                CategoryItemsViewHolder(CategoryCategorylistBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        }
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)){
            CATEGORY_TITLE-> (holder as CategoryTitleViewHolder).bind(position)
            else->
                (holder as CategoryItemsViewHolder).bind(position)
        }
    }
    override fun getItemCount(): Int =list.size
    inner class CategoryTitleViewHolder(val binding : CategoryTitleItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(pos:Int){
            binding.textView2.text=list[pos].categoryName.toString()
        }
    }
    inner class CategoryItemsViewHolder(val binding : CategoryCategorylistBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(pos:Int){
            binding.recycleHorizontal.adapter=
                list[pos].songList?.let {
                    SongHorizontalListAdapter(context, it){

                    }
                }
            binding.recycleHorizontal.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            binding.recycleHorizontal.hasFixedSize()
        }
    }
    override fun getItemViewType(position: Int): Int = if (list[position].isCategoryList) CATEGORY_LIST else CATEGORY_TITLE
}
