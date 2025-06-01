package dang.zhou.cun.shoping.ui.photo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dang.zhou.cun.shoping.data.Photo
import dang.zhou.cun.shoping.repo.PhotoRepository
import kamal.aishwarya.weather.utils.DateUtil.toFormattedDate
import java.text.NumberFormat

/*
* 项目名称: Shopping
* 包名: dang.zhou.cun.shoping.ui.photo
* 日期: 2025/6/1 时间: 16:05
* 作者: bobowg
* 备注：
*/


class PhotoEntryViewModel(private val photoRepository: PhotoRepository) : ViewModel() {
    var photoUiState by mutableStateOf(PhotoUiState())
        private set

    // 更新片功能
    fun updateUiState(photoDetails: PhotoDetails) {
        photoUiState =
            PhotoUiState(photoDetails = photoDetails, isEntryValid = validateInput(photoDetails))
    }

    suspend fun savePhoto() {
        if (validateInput()) {
            photoRepository.insert(photo = photoUiState.photoDetails.toPhoto())
        }
    }

    private fun validateInput(uiState: PhotoDetails = photoUiState.photoDetails): Boolean {
        return with(uiState) {
            url < 1 && price.isNotBlank() && date.isNotBlank()
        }
    }
}

data class PhotoUiState(
    val photoDetails: PhotoDetails = PhotoDetails(),
    val isEntryValid: Boolean = false
)

data class PhotoDetails(
    val id: Int = 0,
    val url: Int = 0,
    val price: String = "",
    val date: String = ""
)

/*
* PhotoDetails转Photo
* */
fun PhotoDetails.toPhoto(): Photo = Photo(
    id = id,
    url = url,
    price = price.toDoubleOrNull() ?: 0.0,
    date = date.toFormattedDate()
)

fun Photo.toPhotoUiState(isEntryValid: Boolean = false): PhotoUiState = PhotoUiState(
    photoDetails = this.toPhotoDetails(),
    isEntryValid = isEntryValid
)

fun Photo.toPhotoDetails(): PhotoDetails = PhotoDetails(
    id = id,
    url = url,
    price = price.toString(),
    date = date.toString()
)

fun Photo.formatedPrice(): String {
    return NumberFormat.getCurrencyInstance().format(price)
}