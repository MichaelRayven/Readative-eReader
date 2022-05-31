package com.example.reader.epub

import android.R.attr.data
import android.R.attr.resource
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.webkit.WebView
import com.example.archives.Archive
import com.example.model.local.entity.Book
import com.example.model.local.util.ArchiveFormat
import com.example.model.local.util.ReadingStatus
import com.example.reader.ReadativeBook
import com.example.reader.ReadativeBookContent
import com.example.reader.ReadativeMetadata
import com.example.reader.ReadativePage
import nl.siegmann.epublib.browsersupport.Navigator
import nl.siegmann.epublib.domain.Resource
import nl.siegmann.epublib.epub.EpubReader
import java.io.IOException
import java.lang.StringBuilder
import java.nio.file.Files
import java.time.OffsetDateTime


class EpubBook(
    context: Context,
    uri: Uri
) : ReadativeBook(context, uri) {
    val book = EpubReader().readEpub(getInputStream())!!

    override val coverPath: String
        get() {
            val path = context.filesDir.resolve("covers").resolve("$checksum.jpg")
            if (!path.exists()) {
                val bitmap = when {
                    book.coverImage != null -> {
                        BitmapFactory.decodeStream(
                            book.coverImage.inputStream
                        )
                    }
                    book.coverPage != null -> {
//                        val bookPath = context.filesDir.resolve("book").resolve(checksum)
//                        val archive = BookFormat.EPUB.archiveFormat?.let {
//                            Archive
//                                .Builder()
//                                .setSource(uri)
//                                .setContext(context)
//                                .setFormat(it)
//                                .build()
//                        }
//                        archive?.extract(bookPath, archive.getArchiveInputStream())
//
//                        val webView = WebView(context.applicationContext)
//                        val pagePath = "file://${bookPath.resolve(book.coverPage.href)}"
//
//                        val settings = webView.settings
//
//                        settings.allowFileAccess = true
//                        settings.loadWithOverviewMode = true
//
//                        webView.webViewClient = object : WebViewClient() {
//                            override fun onPageFinished(view: WebView?, url: String?) {
//                                val bitmap = Bitmap.createBitmap(
//                                    200,
//                                    200,
//                                    Bitmap.Config.RGB_565
//                                )
//                                val canvas = Canvas(bitmap)
//                                view?.draw(canvas)
//
//                                if (bitmap != null) {
//                                    path.parentFile?.mkdirs()
//                                    Files.newOutputStream(path.toPath()).use { output ->
//                                        if (!bitmap.compress(
//                                                Bitmap.CompressFormat.JPEG,
//                                                90,
//                                                output
//                                            )
//                                        ) {
//                                            throw IOException("Couldn't save cover image bitmap")
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                        webView.loadUrl(pagePath)
                        null
                    }
                    else -> {null}
                }
                if (bitmap != null) {
                    path.parentFile?.mkdirs()
                    Files.newOutputStream(path.toPath()).use { output ->
                        if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 90, output)) {
                            throw IOException("Couldn't save cover image bitmap")
                        }
                    }
                }
            }
            return path.absolutePath
        }

    override val metadata: ReadativeMetadata
        get() {
            return EpubMetadata(book.metadata)
        }

    override val pageCount: Int
        get() {
            return book.contents.size
        }

    override fun getBookContents(widthPx: Int, heightPx: Int): ReadativeBookContent {
        val path = context.filesDir.resolve("book")
        if (!path.exists()) {
            val archive =
                Archive.Builder().setSource(uri).setContext(context).setFormat(ArchiveFormat.ZIP)
                    .build()
            archive?.extract(path, archive.getArchiveInputStream())
        }
        val content = StringBuilder()
        val regex = Regex("<body>.*</body>", setOf(RegexOption.DOT_MATCHES_ALL, RegexOption.MULTILINE))
        for (page in book.contents) {
            if (content.isEmpty()) {
                content.append(String(page.data))
            } else {
                content.insert(
                    (content.length - 8).coerceAtLeast(0),
                    regex.find(String(page.data))?.value
                )
            }
        }
        return ReadativeBookContent.EPUBContents(
            content.toString(),
            Uri.fromFile(path).toString()
        )
    }

    override fun getPage(index: Int, widthPx: Int, heightPx: Int): ReadativePage {
        val path = context.filesDir.resolve("book")
        if (!path.exists()) {
            val archive =
                Archive.Builder().setSource(uri).setContext(context).setFormat(ArchiveFormat.ZIP)
                    .build()
            archive?.extract(path, archive.getArchiveInputStream())
        }

        return ReadativePage.HtmlPage(
            path.resolve(book.contents[index].href).absolutePath
        )
    }

    override fun close() {}

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
}