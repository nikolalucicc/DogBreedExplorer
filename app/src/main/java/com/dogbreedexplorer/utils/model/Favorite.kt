package com.dogbreedexplorer.utils.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favorite (@PrimaryKey val id: String,
                     @ColumnInfo(name = "user_id") val user_id: String,
                     @ColumnInfo(name = "image_id") val image_id: String,
                     @ColumnInfo(name = "sub_id") val sub_id: String?,
                     @ColumnInfo(name = "created_at") val created_at: String,
                     @ColumnInfo(name = "image") val image: Image
)