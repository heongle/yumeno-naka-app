package com.yumenonaka.ymnkapp.utility

import android.content.ContentValues
import android.content.Context
import android.media.MediaScannerConnection
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import java.io.File
import java.io.FileOutputStream

object StoreImagesUtility {
    fun saveImages(context: Context, imageName: String, byteArray: ByteArray, relativePath: String? = null) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val resolver = context.contentResolver
            val values = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, imageName)
                put(MediaStore.Images.Media.MIME_TYPE, if(imageName.endsWith("png")) "image/png" else "image/jpeg")
                if(relativePath != null) {
                    put(MediaStore.Images.Media.RELATIVE_PATH, relativePath)
                }
            }
            val uri = resolver.insert(MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL), values)
            resolver.openOutputStream(uri!!).use {
                it?.write(byteArray)
            }
        } else {
            @Suppress("DEPRECATION")
            val imageDir = Environment.getExternalStoragePublicDirectory(relativePath ?: Environment.DIRECTORY_PICTURES)
            if (!imageDir.exists()) {
                imageDir.mkdir()
            }
            val imageFile = File(imageDir, imageName)
            FileOutputStream(imageFile).use {
                it.write(byteArray)
            }
            MediaScannerConnection.scanFile(context, arrayOf(imageFile.absolutePath), null, null)
        }
    }
}
