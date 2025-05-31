package dang.zhou.cun.shoping.data

import android.content.Context
import dang.zhou.cun.shoping.repo.OfflinePhotoRepository
import dang.zhou.cun.shoping.repo.PhotoRepository

/*
* 项目名称: Shopping
* 包名: dang.zhou.cun.shoping.data
* 日期: 2025/5/31 时间: 14:27
* 作者: bobowg
* 备注：
*/


interface AppContainer {
    val photoRepository: PhotoRepository
}

class AppDataContainer(private val context: Context): AppContainer{
    override val photoRepository: PhotoRepository by lazy {
        OfflinePhotoRepository(AppDataBase.getDatabase(context).daoPhoto())
    }

}