package dang.zhou.cun.shoping.data

import androidx.annotation.DrawableRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "photo_table"
)
data class Photo(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "image_url")
    val url: Int,
    @ColumnInfo(name = "price")
    val price: Double,
    @ColumnInfo(name = "date")
    val date: String
)