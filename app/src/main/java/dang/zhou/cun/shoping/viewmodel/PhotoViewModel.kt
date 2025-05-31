package dang.zhou.cun.shoping.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.Factory
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dang.zhou.cun.shoping.data.Photo
import dang.zhou.cun.shoping.repo.PhotoRepository
import kotlinx.coroutines.launch

/*
* 项目名称: Shopping
* 包名: dang.zhou.cun.shoping.viewmodel
* 日期: 2025/5/31 时间: 10:44
* 作者: bobowg
* 备注：
*/


class PhotoViewModel(private val repository: PhotoRepository) : ViewModel() {
    val allPhotos: LiveData<List<Photo>> = repository.getPhotos().asLiveData()
    fun insert(photo: Photo) = viewModelScope.launch {
        repository.insert(photo)
    }
}

class PhotoViewModelFactory(private val repository: PhotoRepository) : Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PhotoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PhotoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}