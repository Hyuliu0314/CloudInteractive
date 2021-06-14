package com.hyuliu.cloudinteractive.application

import android.app.Application
import android.graphics.Bitmap
import android.util.LruCache


class CloudInteractiveApp:Application() {

    private var maxSize = 0
    private var cacheSize = 0
    private lateinit var cache: LruCache<String,Bitmap>

    override fun onCreate() {
        super.onCreate()
        maxSize = (Runtime.getRuntime().maxMemory() / 1024).toInt()
        cacheSize = maxSize / 8

        cache = object : LruCache<String,Bitmap>(maxSize) {
            override fun sizeOf(key: String?, value: Bitmap?): Int {
                if (value != null){
                    return value.byteCount / 1024
                }
                return super.sizeOf(key, value)
            }
        }

    }

    fun getImgCache(): LruCache<String,Bitmap>{
        return cache
    }

}