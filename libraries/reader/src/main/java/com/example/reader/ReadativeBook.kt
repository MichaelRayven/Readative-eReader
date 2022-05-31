package com.example.reader

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import com.example.model.local.util.FileSize
import java.io.InputStream
import java.security.DigestInputStream
import java.security.MessageDigest
import com.example.model.local.entity.Book
import org.apache.commons.codec.binary.Hex
import org.apache.commons.codec.digest.DigestUtils

abstract class ReadativeBook(
    protected val context: Context,
    protected val uri: Uri
) {
    val checksum: String

    init {
        checksum = checksumSHA()
    }

    protected fun getInputStream(): InputStream? {
        return context.contentResolver.openInputStream(uri)
    }

    protected fun getFileName(): String {
        val proj = arrayOf(MediaStore.Files.FileColumns.TITLE)
        val cursor: Cursor? = context.contentResolver.query(uri, proj, null, null, null)
        val fileName = if (cursor != null && cursor.count != 0) {
            val columnIndex: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.TITLE)
            cursor.moveToFirst()
            cursor.getString(columnIndex)
        } else null
        cursor?.close()
        return fileName ?: ""
    }

    private fun checksumSHA(): String {
        return String(Hex.encodeHex(DigestUtils.sha256(getInputStream())))
    }

    val size: FileSize
        get() = FileSize(context.contentResolver.openFileDescriptor(uri, "r")?.statSize ?: 0)

    abstract val coverPath: String
    abstract val metadata: ReadativeMetadata
    abstract val pageCount: Int
    abstract fun close()
    abstract fun toBook(): Book
    abstract fun getPage(index: Int, widthPx: Int, heightPx: Int): ReadativePage
    abstract fun getBookContents(widthPx: Int, heightPx: Int): ReadativeBookContent
}