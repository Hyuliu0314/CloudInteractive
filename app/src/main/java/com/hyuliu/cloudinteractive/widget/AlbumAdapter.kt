package com.hyuliu.cloudinteractive.widget

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.Volley
import com.hyuliu.cloudinteractive.R
import com.hyuliu.cloudinteractive.viewmodel.GalleryViewModel

class AlbumAdapter(val viewModel:GalleryViewModel,val onAlbumClickListener: OnAlbumClickListener):RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

    inner class AlbumViewHolder(view: View):RecyclerView.ViewHolder(view){
        private val album_layout = view.findViewById<ConstraintLayout>(R.id.album_layout)
        private val tv_id = view.findViewById<TextView>(R.id.tv_id)
        private val tv_title = view.findViewById<TextView>(R.id.tv_title)
        private val img_thumbnail = view.findViewById<ImageView>(R.id.img_thumbnail)

        fun bind(position:Int){
            album_layout.setOnClickListener {
                onAlbumClickListener.onClick(viewModel.albumList.value!![position])
            }
            tv_id.text = viewModel.albumList.value!![position].id
            tv_title.text = viewModel.albumList.value!![position].title
            val thumbnailURL = viewModel.albumList.value!![position].thumbnailUrl

            val bitmap = viewModel.cache.get(thumbnailURL)
            if (bitmap != null){
                img_thumbnail.setImageBitmap(bitmap)
            }
            else{
                viewModel.loadImg(thumbnailURL,position)
            }
        }

        fun bindImg(position: Int){
            val url = viewModel.albumList.value!![position].thumbnailUrl
            img_thumbnail.setImageBitmap(viewModel.cache.get(url))
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.album_layout,parent,false)
        return AlbumViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun onBindViewHolder(
        holder: AlbumViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()){
            super.onBindViewHolder(holder, position, payloads)
        }
        else{
            holder.bindImg(position)
        }
    }

    override fun getItemCount(): Int {
        val count = viewModel.albumList.value?.size
        if (count != null){
            return count
        }
        return 0
    }

}