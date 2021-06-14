package com.hyuliu.cloudinteractive.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyuliu.cloudinteractive.api.ApiUtil
import com.hyuliu.cloudinteractive.repository.ContentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ContentViewModel(val repository: ContentRepository):ViewModel() {

    val id = MutableLiveData<String>()
    val title = MutableLiveData<String>()
    val thumbnailUrl = MutableLiveData<String>()
    val bitmap = MutableLiveData<Bitmap>()
    val cache = repository.cache

    init {
        id.value = repository.id
        title.value = repository.title
        thumbnailUrl.value = repository.thumbnailUrl
        if (cache.get(repository.thumbnailUrl) != null){
            bitmap.value = cache.get(repository.thumbnailUrl)
        }
        else{
            loadImg()
        }
    }

    private fun loadImg(){
        viewModelScope.launch(Dispatchers.IO){
            val _bitmap = ApiUtil.loadBitmapFromURL(repository.thumbnailUrl)
            withContext(Dispatchers.Main){
                cache.put(repository.thumbnailUrl,_bitmap)
                bitmap.value = _bitmap!!
            }
        }
    }



}