package com.hyuliu.cloudinteractive.viewmodel

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyuliu.cloudinteractive.api.ApiUtil
import com.hyuliu.cloudinteractive.model.Album
import com.hyuliu.cloudinteractive.repository.GalleryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray

class GalleryViewModel(val repository: GalleryRepository):ViewModel() {

    val imgBitmap = MutableLiveData<Bitmap>()
    val albumList = MutableLiveData<ArrayList<Album>>()
    val loadState = MutableLiveData<Boolean>()
    val cache = repository.imgCache
    val position = MutableLiveData<Int>()

    init {
        loadState.value = true
        callApi()
    }

    private fun callApi(){
        viewModelScope.launch(Dispatchers.IO){
            val _result = ApiUtil.getMethod(repository.targetURL)
            withContext(Dispatchers.Main){
                val templist = parseApiResult(_result)
                if (templist != null){
                    albumList.value = templist!!
                    loadState.value = false
                }
            }
        }
    }

    fun loadImg(url:String,_position:Int){
        viewModelScope.launch(Dispatchers.IO){
            val _imgBitmap = ApiUtil.loadBitmapFromURL(url)
            withContext(Dispatchers.Main){
                println(_imgBitmap)
                cache.put(url,_imgBitmap)
                imgBitmap.value = _imgBitmap!!
                position.value = _position
            }
        }
    }

    private fun parseApiResult(result: String):ArrayList<Album>?{
        if (result.isNotEmpty()){
            val list = ArrayList<Album>()
            try {
                val array = JSONArray(result)
                for (i in 0 until array.length()){
                    val obj = array.getJSONObject(i)
                    list.add(
                        Album(
                            obj.getString("albumId"),
                            obj.getString("id"),
                            obj.getString("title"),
                            obj.getString("url"),
                            obj.getString("thumbnailUrl"),
                        )
                    )
                }
                return list
            }catch (e: Exception) {
                Log.e("API Parse Error", e.toString())
            }
        }
        return null
    }

}