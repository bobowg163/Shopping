package dang.zhou.cun.shoping.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/*
* 项目名称: Shopping
* 包名: dang.zhou.cun.Shoping.data
* 日期: 5/30/25 时间: 9:48 AM
* 作者: bobowg
* 备注：
*/

@Dao
interface DaoPhoto {
    @Query("SELECT * FROM photo_table ORDER BY id ASC")
    fun getPhotos():Flow<List<Photo>>

    @Query("SELECT * FROM photo_table WHERE id = :id")
    fun getPhotosStream(id: Int): Flow<Photo>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(photo: Photo)

    @Delete
    suspend fun delete(photo: Photo)

    @Update
    suspend fun update(photo: Photo)
}