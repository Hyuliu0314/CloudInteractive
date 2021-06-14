package com.hyuliu.cloudinteractive.activity

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.hyuliu.cloudinteractive.R

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val btnRequest:Button by lazy { findViewById(R.id.btn_request) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnRequest.setOnClickListener(this)
    }

    private fun goToNextActivity(){
        val intent = Intent(this,GalleryActivity::class.java)
        startActivity(intent)
    }

    override fun onClick(v: View?) {
        when(v){
            btnRequest -> {
                if (getInternetConnection()) goToNextActivity()
                else Toast.makeText(this,"未連接網路",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getInternetConnection():Boolean{
        var isConnect = false
        val connManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val currentNetwork = connManager.activeNetwork
        if (currentNetwork != null) isConnect = true
        return isConnect
    }

}