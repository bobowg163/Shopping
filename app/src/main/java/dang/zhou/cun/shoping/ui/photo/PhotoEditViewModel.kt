package dang.zhou.cun.shoping.ui.photo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dang.zhou.cun.shoping.repo.PhotoRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/*
* 项目名称: Shopping
* 包名: dang.zhou.cun.shoping.ui.photo
* 作者: bobowg
* 日期: 2025/6/7 时间: 23:35
* 备注:
*/

class PhotoEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val photoRepository: PhotoRepository
) : ViewModel() {
    var photoUiState by mutableStateOf(PhotoUiState())
        private set
    private val photoId: Int = checkNotNull(savedStateHandle[PhotoEditDestination.photoIdArg])

    init {
        viewModelScope.launch {
            photoUiState = photoRepository.getPhotosStream(photoId).filterNotNull().first()
                .toPhotoUiState(true)
        }
    }

    suspend fun updatePhoto() {
        if (validateInput(photoUiState.photoDetails)) {
            photoRepository.update(photoUiState.photoDetails.toPhoto())
        }
    }

    fun updateUiState(photoDetails: PhotoDetails) {
        photoUiState =
            PhotoUiState(photoDetails = photoDetails, isEntryValid = validateInput(photoDetails))
    }

    private fun validateInput(photoDetails: PhotoDetails = photoUiState.photoDetails): Boolean {
        return with(photoDetails) {
            price.isNotBlank() && date.isNotBlank()
        }
    }
}

