package com.dogbreedexplorer.utils.converter

import androidx.room.TypeConverter
import com.dogbreedexplorer.utils.model.Image
import com.google.gson.Gson

class ImageConverter {

    @TypeConverter
    fun fromImage(image: Image): String {
        return Gson().toJson(image)
    }

    @TypeConverter
    fun toImage(json: String): Image {
        return Gson().fromJson(json, Image::class.java)
    }

}