package com.example.reader

import android.content.Context
import android.net.Uri
import android.os.ParcelFileDescriptor
import android.os.ParcelFileDescriptor.MODE_READ_ONLY
import android.util.Log
import com.example.model.local.util.FileSize
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.security.DigestInputStream
import java.security.MessageDigest

abstract class ReadativeBook(
    protected val context: Context,
    protected val uri: Uri?,
    protected val file: File?
) {
    protected fun getInputStream(): InputStream? {
        return when {
            uri != null -> {
                context.contentResolver.openInputStream(uri)
            }
            file != null -> {
                FileInputStream(file)
            }
            else -> null
        }
    }

    val checksum: String
    init {
        checksum = checksumSHA()
    }
    private fun checksumSHA(): String {
        var md = MessageDigest.getInstance("SHA-256")

         DigestInputStream(getInputStream(), md).use { dis ->
            while (dis.read() != -1);
            md = dis.messageDigest
        }

        val result = StringBuilder()
        for (b in md.digest()) {
            result.append(String.format("%02x", b))
        }
        return result.toString()
    }

    abstract val coverPath: String
    abstract val metadata: ReadativeMetadata
    val size: FileSize
        get() {
            return when {
                uri != null -> {
                    FileSize(context.contentResolver.openFileDescriptor(uri, "r")?.statSize ?: 0)
                }
                file != null -> {
                    FileSize(file.length())
                }
                else -> FileSize(0)
            }
        }

    abstract fun close()
    abstract fun toBook(): com.example.model.local.entity.Book
    abstract fun getPage(index: Int): ReadativePage
    abstract fun pageCount(): Int
}