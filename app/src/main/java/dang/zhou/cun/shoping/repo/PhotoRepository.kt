package dang.zhou.cun.shoping.repo

import androidx.annotation.WorkerThread
import dang.zhou.cun.shoping.data.DaoPhoto
import dang.zhou.cun.shoping.data.Photo
import kotlinx.coroutines.flow.Flow

/*
* 项目名称: Shopping
* 包名: dang.zhou.cun.shoping.repo
* 日期: 2025/5/31 时间: 10:25
* 作者: bobowg
* 备注：
*/

interface PhotoRepository {
    fun getPhotos(): Flow<List<Photo>>
    fun getPhotosStream(id: Int): Flow<Photo?>
    suspend fun insert(photo: Photo)
    suspend fun update(photo: Photo)
    suspend fun delete(photo: Photo)
}