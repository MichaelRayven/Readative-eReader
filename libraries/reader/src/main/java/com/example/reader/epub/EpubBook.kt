package com.example.reader.epub

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.net.Uri
import android.os.ParcelFileDescriptor
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.archives.Archive
import com.example.model.local.entity.Book
import com.example.model.local.util.ArchiveFormat
import com.example.model.local.util.ReadingStatus
import com.example.reader.ReadativeBook
import com.example.reader.ReadativeMetadata
import com.example.reader.ReadativePage
import com.izettle.html2bitmap.Html2Bitmap
import com.izettle.html2bitmap.content.WebViewContent
import nl.siegmann.epublib.epub.EpubReader
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.time.OffsetDateTime
import kotlin.io.path.absolutePathString
import kotlin.io.path.exists


class EpubBook(
    private val filesDir: Path,
    context: Context,
    uri: Uri?,
    file: File?
) : ReadativeBook(context, uri, file) {
    override val coverPath: String
        get() {
            val path = filesDir.resolve("covers").resolve("$checksum.jpg")
            if (!path.exists()) {
                val input = getInputStream()
                val book = EpubReader().readEpub(input)
                val bitmap = when {
                    book.coverImage != null -> {
                        BitmapFactory.decodeStream(
                            book.coverImage.inputStream
                        )
                    }
                    book.coverPage != null -> {
//                        BitmapFactory.decodeStream(
//                            book.coverPage.inputStream
//                        )
                        Html2Bitmap.Builder(
                            context, WebViewContent.html(
                                String(book.coverPage.data)
                            )
                        ).setBitmapWidth(300).build().bitmap
                    }
                    else -> {
                        null
                    }
                }
                path.parent.toFile().mkdirs()
                Files.newOutputStream(path).use { output ->
                    if (bitmap?.compress(Bitmap.CompressFormat.JPEG, 90, output) == false) {
                        throw IOException("Couldn't save cover image bitmap")
                    }
                }
            }
            return path.absolutePathString()
        }
    override val metadata: ReadativeMetadata
        get() {
            val input = getInputStream()
            val book = EpubReader().readEpub(input)
            return EpubMetadata(book.metadata)
        }

    override fun close() {
        val path = filesDir.resolve("temp").resolve(checksum)
        path.toFile().deleteRecursively()
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

    override fun getPage(index: Int): ReadativePage {
        val path = filesDir.resolve("book").resolve(checksum)
        val input = getInputStream()
        val book = EpubReader().readEpub(input)
        val href = book.spine.spineReferences[index].resource.href
        if (!path.exists()) {
            val archive = if (uri != null) {
                Archive.Builder().setSource(uri).setContext(context).setFormat(ArchiveFormat.ZIP).build()
            } else if (file != null) {
                Archive.Builder().setSource(file).setFormat(ArchiveFormat.ZIP).build()
            } else null

            archive?.extract(path.toFile(), archive.getArchiveInputStream())
        }
        return ReadativePage.HtmlPage(path.resolve(href).absolutePathString())
    }

    override fun pageCount(): Int {
        val input = getInputStream()
        val book = EpubReader().readEpub(input)
        return book.spine.size()
    }

}