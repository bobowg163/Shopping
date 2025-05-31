package dang.zhou.cun.shoping.repo

import dang.zhou.cun.shoping.data.DaoPhoto
import dang.zhou.cun.shoping.data.Photo
import kotlinx.coroutines.flow.Flow

/*
* 项目名称: Shopping
* 包名: dang.zhou.cun.shoping.repo
* 日期: 2025/5/31 时间: 14:24
* 作者: bobowg
* 备注：
*/


class OfflinePhotoRepository(private val daoPhoto: DaoPhoto) : PhotoRepository {
    override fun getPhotos(): Flow<List<Photo>> = daoPhoto.getPhotos()

    override fun getPhotosStream(id: Int): Flow<Photo?> = daoPhoto.getPhotosStream(id)

    override suspend fun insert(photo: Photo) = daoPhoto.insert(photo)

    override suspend fun update(photo: Photo) = daoPhoto.update(photo)

    override suspend fun delete(photo: Photo) = daoPhoto.delete(photo)
}