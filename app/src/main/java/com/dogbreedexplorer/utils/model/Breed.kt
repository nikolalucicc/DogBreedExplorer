package com.dogbreedexplorer.ui.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.reactivex.annotations.NonNull

@Entity
data class Breed(@PrimaryKey val id: String,
                 @ColumnInfo(name = "name") val name: String?,
                 @ColumnInfo(name = "origin") val origin: String?,
                 @ColumnInfo(name = "breed_for") val breed_for: String?,
                 @ColumnInfo(name = "breed_group") val breed_group: String?,
                 @ColumnInfo(name = "life_span") val life_span: String?,
                 @ColumnInfo(name = "temperament") val temperament: String?,
                 @ColumnInfo(name = "reference_image_id") val reference_image_id: String?)
