package com.example.roomdatabase.presentation.Utils

import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream

fun imageCompress(imageData : ByteArray): ByteArray{
    val bitmap = BitmapFactory.decodeByteArray(imageData,0,imageData.size)
    val outputStream= ByteArrayOutputStream()
    bitmap.compress(android.graphics.Bitmap.CompressFormat.JPEG,50,outputStream)
    return outputStream.toByteArray()


}