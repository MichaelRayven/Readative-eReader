package com.example.reader.pdf

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.ParcelFileDescriptor
import android.os.ParcelFileDescriptor.MODE_READ_ONLY
import com.example.model.local.entity.Book
import com.example.model.local.util.ReadingStatus
import com.example.reader.ReadativeBook
import com.example.reader.ReadativeMetadata
import com.example.reader.ReadativePage
import com.shockwave.pdfium.PdfDocument
import com.shockwave.pdfium.PdfiumCore
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.time.OffsetDateTime
import kotlin.io.path.absolutePathString
import kotlin.io.path.exists

class PdfBook(
    private val filesDir: Path,
    context: Context,
    uri: Uri?,
    file: File?
): ReadativeBook(context, uri, file) {
    val core: PdfiumCore = PdfiumCore(context)
    val book: PdfDocument = core.newDocument(if (uri != null) {
            context.contentResolver.openFileDescriptor(uri, "r")
        } else {
            ParcelFileDescriptor.open(file, MODE_READ_ONLY)
        })

    override val coverPath: String
        get() {
            val path = filesDir.resolve("covers").resolve("$checksum.jpg")
            if (!path.exists()) {
                core.openPage(book, 0)

                val width = core.getPageWidthPoint(book, 0)
                val height = core.getPageHeightPoint(book, 0)

                val bitmap = Bitmap.createBitmap(
                    width, height,
                    Bitmap.Config.RGB_565
                )
                core.renderPageBitmap(
                    book, bitmap, 0, 0, 0,
                    width, height
                )

                path.parent.toFile().mkdirs()
                Files.newOutputStream(path).use { output ->
                    if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 90, output)) {
                        throw IOException("Couldn't save cover image bitmap")
                    }
                }
                core.closeDocument(book)
            }
            return path.absolutePathString()
        }
    override val metadata: ReadativeMetadata
        get() {
            val meta = core.getDocumentMeta(book)
            return PdfMetadata(meta)
        }

    override fun toBook(): Book {
        return Book(
            0,
            checksum,
            metadata.title,
            metadata.description,
            coverPath,
            ReadingStatus.UNREAD,
            0f,
            metadata.published,
            OffsetDateTime.now()
        )
    }

    override fun close() {
        core.closeDocument(book)
    }

    override fun getPage(index: Int): ReadativePage {
        core.openPage(book, index)

        val width = core.getPageWidthPoint(book, index)
        val height = core.getPageHeightPoint(book, index)

        val bitmap = Bitmap.createBitmap(
            width, height,
            Bitmap.Config.RGB_565
        )
        core.renderPageBitmap(
            book, bitmap, index, 0, 0,
            width, height
        )

        return ReadativePage.ImagePage(bitmap)
    }

    override fun pageCount(): Int {
        return core.getPageCount(book)
    }
}