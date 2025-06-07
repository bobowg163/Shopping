package dang.zhou.cun.shoping.ui.photo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dang.zhou.cun.shoping.repo.PhotoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/*
* 项目名称: Shopping
* 包名: dang.zhou.cun.shoping.ui.photo
* 日期: 2025/6/3 时间: 08:22
* 作者: bobowg
* 备注：
*/


class PhotoDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val photoRepository: PhotoRepository
) : ViewModel() {
    private val photoId: Int = checkNotNull(savedStateHandle[PhotoDetailsDestination.photoIdArg])
    val uiState: StateFlow<PhotoDetailsUiState> =
        photoRepository.getPhotosStream(photoId).filterNotNull().map {
            PhotoDetailsUiState(outOfStock = it.price <= 0, photoDetails = it.toPhotoDetails())
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = PhotoDetailsUiState()
        )

    fun reduceQuantityByOne() {
        viewModelScope.launch {
            val currentPhoto = uiState.value.photoDetails.toPhoto()
            if (currentPhoto.price > 0){
                photoRepository.update(currentPhoto.copy(price = currentPhoto.price - 1))
            }
        }
    }

    suspend fun deletePhoto() {
        photoRepository.delete(uiState.value.photoDetails.toPhoto())
    }


    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

}


data class PhotoDetailsUiState(
    val outOfStock: Boolean = true,
    val photoDetails: PhotoDetails = PhotoDetails()
)