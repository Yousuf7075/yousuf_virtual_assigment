package com.example.virtual_assigment.helper

import android.content.Context
import android.net.Uri
import android.provider.MediaStore

fun getRealPathFromURI(context: Context, contentURI: Uri): String? {
    val result: String?
    val cursor = context.contentResolver.query(contentURI, null, null, null, null)
    if (cursor == null) { // Source is Dropbox or other similar local file path
        result = contentURI.path
    } else {
        cursor.moveToFirst()
        val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
        result = cursor.getString(idx)
        cursor.close()
    }
    return result
}