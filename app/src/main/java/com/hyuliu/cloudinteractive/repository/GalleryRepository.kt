package com.hyuliu.cloudinteractive.repository

import android.graphics.Bitmap
import android.util.LruCache
import com.hyuliu.cloudinteractive.model.Album

class GalleryRepository(val imgCache: LruCache<String, Bitmap>):IRepository {
    val targetURL = "https://jsonplaceholder.typicode.com/photos"
}