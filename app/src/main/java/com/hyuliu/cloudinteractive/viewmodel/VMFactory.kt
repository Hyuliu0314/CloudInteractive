package com.hyuliu.cloudinteractive.viewmodel

import android.app.Application
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.hyuliu.cloudinteractive.repository.ContentRepository
import com.hyuliu.cloudinteractive.repository.GalleryRepository
import com.hyuliu.cloudinteractive.repository.IRepository

class VMFactory constructor(private val application: Application,val repository:IRepository?) : ViewModelProvider.AndroidViewModelFactory(application) {

    companion object{
        @JvmStatic
        fun <T: ViewModel> viewModel(activity: AppCompatActivity, clazz: Class<T>,data: IRepository? = null): T{
            return viewModel(
                activity,
                activity.application!!,
                clazz,
                data
            )
        }

        @JvmStatic
        fun <T: ViewModel> viewModel(vmStoreOwner: ViewModelStoreOwner, @NonNull application: Application, clazz: Class<T>,data: IRepository? = null):T {
            return ViewModelProvider(vmStoreOwner,
                VMFactory(application,data)
            ).get(clazz)
        }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(GalleryViewModel::class.java) -> GalleryViewModel(repository as GalleryRepository) as T
            modelClass.isAssignableFrom(ContentViewModel::class.java) -> ContentViewModel(repository as ContentRepository) as T
            else -> super.create(modelClass)
        }
    }

}