package dang.zhou.cun.shoping.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dang.zhou.cun.shoping.data.Photo
import dang.zhou.cun.shoping.repo.PhotoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/*
* 项目名称: Shopping
* 包名: dang.zhou.cun.shoping.ui.home
* 日期: 2025/5/31 时间: 22:00
* 作者: bobowg
* 备注：
*/


class HomeViewModel(photoRepository: PhotoRepository) : ViewModel() {
    val homeUiState: StateFlow<HomeUiState> =
        photoRepository.getPhotos().map { HomeUiState(it) }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = HomeUiState()
        )

    companion object{
       private const val TIMEOUT_MILLIS = 5_000L
    }
}


data class HomeUiState(
    val photoList: List<Photo> = listOf()
)