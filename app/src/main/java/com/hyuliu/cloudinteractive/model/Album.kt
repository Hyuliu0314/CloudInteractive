package com.hyuliu.cloudinteractive.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Album(
    val albumId:String,
    val id:String,
    val title:String,
    val url:String,
    val thumbnailUrl:String
): Parcelable
