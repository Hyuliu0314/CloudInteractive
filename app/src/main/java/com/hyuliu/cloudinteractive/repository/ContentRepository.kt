package com.hyuliu.cloudinteractive.repository

import android.content.Intent
import android.graphics.Bitmap
import android.util.LruCache
import com.hyuliu.cloudinteractive.model.Album

class ContentRepository(intent: Intent,imgCache: LruCache<String, Bitmap>):IRepository {
    private val album = intent.getParcelableExtra<Album>("Album")

    val id = album.id
    val title = album.title
    val thumbnailUrl = album.thumbnailUrl
    val cache = imgCache

}