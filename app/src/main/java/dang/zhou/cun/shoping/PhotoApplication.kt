package dang.zhou.cun.shoping

import android.app.Application
import dang.zhou.cun.shoping.data.AppContainer
import dang.zhou.cun.shoping.data.AppDataContainer

/*
* 项目名称: Shopping
* 包名: dang.zhou.cun.shoping
* 日期: 2025/5/31 时间: 14:32
* 作者: bobowg
* 备注：
*/


class PhotoApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}