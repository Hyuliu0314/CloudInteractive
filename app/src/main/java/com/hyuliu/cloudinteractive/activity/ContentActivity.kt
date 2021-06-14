package com.hyuliu.cloudinteractive.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import com.hyuliu.cloudinteractive.R
import com.hyuliu.cloudinteractive.application.CloudInteractiveApp
import com.hyuliu.cloudinteractive.repository.ContentRepository
import com.hyuliu.cloudinteractive.viewmodel.ContentViewModel
import com.hyuliu.cloudinteractive.viewmodel.VMFactory

class ContentActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var viewModel:ContentViewModel

    private val tv_content_id:TextView by lazy { findViewById(R.id.tv_content_id) }
    private val tv_content_title:TextView by lazy { findViewById(R.id.tv_content_title) }
    private val img_content_pic:ImageView by lazy { findViewById(R.id.img_content_pic) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)

        if (intent != null){
            viewModel = VMFactory.viewModel(this,ContentViewModel::class.java,ContentRepository(intent,
                (applicationContext as CloudInteractiveApp).getImgCache()))
        }

        setObserver()

        img_content_pic.setOnClickListener(this)

    }

    private fun setObserver(){
        viewModel.id.observe(this, Observer {
            tv_content_id.text = it
        })
        viewModel.title.observe(this, Observer {
            tv_content_title.text = it
        })
        viewModel.bitmap.observe(this, Observer {
            img_content_pic.setImageBitmap(it)
        })
    }

    override fun onClick(v: View?) {
        when(v){
            img_content_pic -> finish()
        }
    }

}