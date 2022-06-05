package com.example.reader.pdf

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.ParcelFileDescriptor
import android.os.ParcelFileDescriptor.MODE_READ_ONLY
import com.example.model.local.entity.Book
import com.example.model.local.util.ReadingStatus
import com.example.reader.ReadativeBook
import com.example.reader.ReadativeBookContent
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
    context: Context,
    pdfiumCore: PdfiumCore,
    uri: Uri
): ReadativeBook(context, uri) {

    private val core: PdfiumCore = pdfiumCore
    private val book: PdfDocument = core.newDocument(context.contentResolver.openFileDescriptor(uri, "r"))

    override val coverPath: String
        get() {
            val path = context.filesDir.resolve("covers").resolve("$checksum.jpg")
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

                path.parentFile?.mkdirs()
                Files.newOutputStream(path.toPath()).use { output ->
                    if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 90, output)) {
                        throw IOException("Couldn't save cover image bitmap")
                    }
                }
            }
            return path.absolutePath
        }
    override val metadata: ReadativeMetadata
        get() {
            val meta = core.getDocumentMeta(book)
            return PdfMetadata(meta)
        }

    override val pageCount: Int
        get() = core.getPageCount(book)

    override fun toBook(): Book {
        return Book(
            0,
            checksum,
            metadata.title.ifEmpty { getFileName() },
            metadata.description,
            coverPath,
            ReadingStatus.UNREAD,
            0f,
            metadata.published,
            OffsetDateTime.now()
        )
    }

    override fun getPage(index: Int, widthPx: Int, heightPx: Int): ReadativePage {
        core.openPage(book, index)

        var width = core.getPageWidthPoint(book, index).toFloat()
        var height = core.getPageHeightPoint(book, index).toFloat()

        val scaleFactor = if (width > widthPx) {
            100f / (width * 100 / widthPx)
        } else {
            100f / (height * 100 / heightPx)
        }

        width *= scaleFactor
        height *= scaleFactor

        val bitmap = Bitmap.createBitmap(
            width.toInt(), height.toInt(),
            Bitmap.Config.RGB_565
        )
        core.renderPageBitmap(
            book, bitmap, index, 0, 0,
            width.toInt(), height.toInt()
        )

        return ReadativePage.ImagePage(bitmap)
    }

    override fun getBookContents(widthPx: Int, heightPx: Int): ReadativeBookContent {
//        val bitmapList = mutableListOf<Bitmap>()
//        for (index in 0 until pageCount) {
//            core.openPage(book, index)
//
//            var width = core.getPageWidthPoint(book, index).toFloat()
//            var height = core.getPageHeightPoint(book, index).toFloat()
//
//            val scaleFactor = if (width > widthPx) {
//                100f / (width * 100 / widthPx)
//            } else {
//                100f / (height * 100 / heightPx)
//            }
//
//            width *= scaleFactor
//            height *= scaleFactor
//
//            val bitmap = Bitmap.createBitmap(
//                width.toInt(), height.toInt(),
//                Bitmap.Config.RGB_565
//            )
//            core.renderPageBitmap(
//                book, bitmap, index, 0, 0,
//                width.toInt(), height.toInt()
//            )
//            bitmapList.add(bitmap)
//        }
//        return ReadativeBookContent.PDFContents(bitmapList)
        return ReadativeBookContent.PDFContents(this)
    }

    override fun close() {
        core.closeDocument(book)
    }
}