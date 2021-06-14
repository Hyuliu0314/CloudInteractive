package com.hyuliu.cloudinteractive.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hyuliu.cloudinteractive.R
import com.hyuliu.cloudinteractive.application.CloudInteractiveApp
import com.hyuliu.cloudinteractive.model.Album
import com.hyuliu.cloudinteractive.repository.GalleryRepository
import com.hyuliu.cloudinteractive.viewmodel.GalleryViewModel
import com.hyuliu.cloudinteractive.viewmodel.VMFactory
import com.hyuliu.cloudinteractive.widget.AlbumAdapter
import com.hyuliu.cloudinteractive.widget.OnAlbumClickListener

class GalleryActivity : AppCompatActivity() {
    private lateinit var viewModel:GalleryViewModel
    private val recyclerView:RecyclerView by lazy { findViewById(R.id.recyclerview) }
    private val progressBar:ProgressBar by lazy { findViewById(R.id.gallery_progressbar) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        viewModel = VMFactory.viewModel(this,GalleryViewModel::class.java,GalleryRepository(
            (applicationContext as CloudInteractiveApp).getImgCache()))

        setObserver()
        setRecyclerView()
    }

    private fun setRecyclerView(){
        recyclerView.adapter = AlbumAdapter(viewModel, object : OnAlbumClickListener{
            override fun onClick(album: Album) {
                val intent = Intent(this@GalleryActivity,ContentActivity::class.java)
                intent.putExtra("Album",album)
                startActivity(intent)
            }
        })
        recyclerView.layoutManager = GridLayoutManager(this,4)
    }

    private fun setObserver(){
        viewModel.albumList.observe(this, Observer {
            recyclerView.adapter?.notifyDataSetChanged()
        })
        viewModel.position.observe(this, Observer {
            val list = ArrayList<Boolean>()
            list.add(true)
            recyclerView.adapter?.notifyItemChanged(it,list)
        })
        viewModel.loadState.observe(this, Observer {
            if (it){
                progressBar.visibility = View.VISIBLE
            }
            else{
                progressBar.visibility = View.GONE
            }
        })
    }

}