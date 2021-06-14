package com.hyuliu.cloudinteractive.widget

import android.content.Intent
import com.hyuliu.cloudinteractive.model.Album

interface OnAlbumClickListener {
    fun onClick(album: Album)
}