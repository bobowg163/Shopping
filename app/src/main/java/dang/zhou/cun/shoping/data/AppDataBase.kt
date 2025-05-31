package dang.zhou.cun.shoping.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/*
* 项目名称: Shopping
* 包名: dang.zhou.cun.shoping.data
* 日期: 2025/5/31 时间: 09:29
* 作者: bobowg
* 备注：
*/
@Database(entities = [Photo::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract fun daoPhoto(): DaoPhoto

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(context, AppDataBase::class.java, "photo_database").build()
                    .also { INSTANCE = it }
            }
        }
    }
}