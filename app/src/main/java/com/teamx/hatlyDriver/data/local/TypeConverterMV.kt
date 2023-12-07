package com.teamx.hatlyDriver.data.local

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

class TypeConverterMV {
    @TypeConverter
    fun toByteArray(bitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        val asdf = outputStream.toByteArray()
        return asdf
    }

    @TypeConverter
    fun toBitmap(byteArray: ByteArray): Bitmap {
        var bitmap: Bitmap? = null
        bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        return bitmap
    }
}


