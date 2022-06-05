package com.example.readative

import android.Manifest
import android.content.ContentUris
import android.content.pm.PackageManager
import android.database.ContentObserver
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.archives.Archive
import com.example.model.local.entity.BookFile
import com.example.model.local.util.ArchiveFormat
import com.example.model.local.util.BookFormat
import com.example.readative.navigation.Navigation
import com.example.readative.navigation.ReadativeScreen
import com.example.readative.theme.ReadativeTheme
import com.example.reader.ReadativeBookReader
import com.example.theme.R
import com.example.usecase.app.AppUseCases
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class ReadativeActivity : ComponentActivity() {
    private var readPermissionGranted = false
    private lateinit var permissionsLauncher: ActivityResultLauncher<String>

    private val namespaceData = mutableMapOf<String, Int>()

    @Inject
    lateinit var bookReader: ReadativeBookReader

    @Inject
    lateinit var appUseCases: AppUseCases

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val windowInsetsController =
            ViewCompat.getWindowInsetsController(window.decorView) ?: return
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())

        setContent {
            ReadativeTheme {
                ReadativeApp()
            }
        }

        permissionsLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { permission ->
                if (permission) {
                    readPermissionGranted = permission
                    initContentObserver()
                    loadBooksIntoDatabase()
                }
            }
        updateOrRequestReadPermission()
        if (readPermissionGranted) {
            initContentObserver()
            loadBooksIntoDatabase()
        }
    }

    private fun updateOrRequestReadPermission() {
        val hasReadPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

        readPermissionGranted = hasReadPermission

        if (!readPermissionGranted) {
            permissionsLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    private fun initContentObserver() {
        val contentObserver = object : ContentObserver(null) {
            override fun onChange(selfChange: Boolean) {
                if (readPermissionGranted) {
                    loadBooksIntoDatabase()
                }
            }
        }
        contentResolver.registerContentObserver(
            MediaStore.Files.getContentUri("external"),
            true,
            contentObserver
        )
    }

    private fun loadBooksIntoDatabase() {
        lifecycleScope.launch {
            val books = loadBooksFromStorage()
//            val archives = loadArchivesFromStorage()
            for (content in books) {
                val format = BookFormat.extensionsMap[content.extension] ?: continue
                processBook(content.path, content.contentUri, format)
            }
//            for (content in archives) {
//                val format =
//                    ArchiveFormat.values().firstOrNull { it.extension == content.extension }
//                        ?: continue
//                val archive =
//                    Archive.Builder().setSource(File(content.path)).setFormat(format).build()
//                if (archive != null) {
//                    processArchive(File(content.path), tempDirectory(content.name), archive)
//                }
//            }
            filesDir.resolve("temp").deleteRecursively()
        }
    }

    private suspend fun processBook(path: String, uri: Uri, format: BookFormat) {
        val dbFile = appUseCases.getBookFileByPath(path)

        if (dbFile == null) {
            val book = bookReader.readBook(uri, format) ?: return
            if (book.size.value == 0f) return

            val dbBook = appUseCases.getBookByChecksum(book.checksum)
            if (dbBook == null) {
                val bookId = appUseCases.addBook(book.toBook())
                book.metadata.authors.forEach { appUseCases.addAuthor(it.toAuthor()) }
                appUseCases.addBookFile(
                    BookFile(
                        0,
                        bookId,
                        path,
                        book.size,
                        format
                    )
                )
            } else {
                appUseCases.addBookFile(
                    BookFile(
                        0,
                        dbBook.id,
                        path,
                        book.size,
                        format
                    )
                )
            }
            book.close()
        }
    }

    private suspend fun processArchive(parentDir: File, tempDir: File, archive: Archive) {
        withContext(Dispatchers.IO)
        {
            val input = archive.getArchiveInputStream()
            archive.entries(input)?.forEach { archiveEntry ->
                if (archiveEntry.isArchive) {
                    val path = archive.extractEntry(tempDir, input, archiveEntry)
                        ?: return@forEach
                    val format = ArchiveFormat.values().firstOrNull {
                        it.extension == path.substringAfterLast('.', "")
                    } ?: return@forEach

                    val archiveInner = Archive
                        .Builder()
                        .setSource(File(path))
                        .setFormat(format).build() ?: return@forEach

                    processArchive(
                        parentDir.resolve(archiveEntry.name),
                        tempDir.resolve(archiveEntry.name),
                        archiveInner
                    )
                } else if (archiveEntry.isBook) {
                    val path = archive.extractEntry(tempDir, input, archiveEntry)
                        ?: return@forEach
                    val format = BookFormat.values().firstOrNull {
                        it.extension == path.substringAfterLast('.', "")
                    } ?: return@forEach
                    processBook(
                        parentDir.resolve(archiveEntry.name).absolutePath,
                        Uri.fromFile(File(path)),
                        format
                    )
                }
            }
        }
    }

    private suspend fun loadArchivesFromStorage(): List<Content> {
        return loadContentByMimeTypes(
            ArchiveFormat.values().map {
                MimeTypeMap.getSingleton().getMimeTypeFromExtension(it.extension)!!
            }.toTypedArray()
        )
    }

    private suspend fun loadBooksFromStorage(): List<Content> {
        return loadContentByMimeTypes(
            BookFormat.values().map {
                MimeTypeMap.getSingleton().getMimeTypeFromExtension(it.extension)!!
            }.toTypedArray()
        )
    }

    private suspend fun loadContentByMimeTypes(mimeTypes: Array<String>): List<Content> {
        return withContext(Dispatchers.IO)
        {
            val collection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            } else {
                MediaStore.Files.getContentUri("external")
            }

            val projection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                arrayOf(
                    MediaStore.Files.FileColumns._ID,
                    MediaStore.Files.FileColumns.MIME_TYPE,
                    MediaStore.Files.FileColumns.DISPLAY_NAME,
                    MediaStore.Files.FileColumns.RELATIVE_PATH
                )
            } else {
                arrayOf(
                    MediaStore.Files.FileColumns._ID,
                    MediaStore.Files.FileColumns.MIME_TYPE,
                    MediaStore.Files.FileColumns.DISPLAY_NAME,
                    MediaStore.Files.FileColumns.DATA
                )
            }

            val selection = StringBuilder()
            repeat(mimeTypes.count()) {
                if (selection.isEmpty()) {
                    selection.append("${MediaStore.Files.FileColumns.MIME_TYPE} = ?")
                } else {
                    selection.append(" OR ${MediaStore.Files.FileColumns.MIME_TYPE} = ?")
                }
            }

            val result = mutableListOf<Content>()
            contentResolver.query(
                collection,
                projection,
                selection.toString(),
                mimeTypes,
                null
            )?.use { cursor ->
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID)
                val mimeTypeColumn = cursor.getColumnIndex(MediaStore.Files.FileColumns.MIME_TYPE)
                val nameColumn = cursor.getColumnIndex(MediaStore.Files.FileColumns.DISPLAY_NAME)
                val pathColumn = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    cursor.getColumnIndex(MediaStore.Files.FileColumns.RELATIVE_PATH)
                } else {
                    cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA)
                }

                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)
                    val mimeType = cursor.getString(mimeTypeColumn)
                    val name = cursor.getString(nameColumn) ?: ""
                    val path = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        Environment.getExternalStorageDirectory()
                            .resolve(cursor.getString(pathColumn))
                            .resolve(name)
                            .absolutePath
                    } else {
                        cursor.getString(pathColumn)
                    }
                    val contentUri = ContentUris.withAppendedId(
                        collection,
                        id
                    )
                    MimeTypeMap.getSingleton()
                        .getExtensionFromMimeType(mimeType)
                        ?.let {
                            result.add(
                                Content(
                                    contentUri,
                                    name,
                                    path,
                                    it
                                )
                            )
                        }
                }
            }
            result
        }
    }

    private fun tempDirectory(child: String): File {
        val count = namespaceData[child] ?: 0

        // Create path
        val dir = filesDir.resolve("temp").resolve("${child}-$count")

        // Change namespace data
        if (count == 0) {
            namespaceData[child] = 1
        } else {
            namespaceData[child] = count + 1
        }

        return dir
    }

    data class Content(
        val contentUri: Uri,
        val name: String,
        val path: String,
        val extension: String
    )
}

@Composable
fun ReadativeSurface(
    navController: NavController,
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            ReadativeTopBar(navController = navController)
        },
        bottomBar = {
            ReadativeBottomNav(navController = navController)
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            content()
        }
    }
}

@Composable
fun ReadativeApp() {
    val navController = rememberNavController()
    Navigation(
        navController = navController,
    )
}

@Composable
fun ReadativeTopBar(
    navController: NavController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val screen = ReadativeScreen.fromRoute(currentRoute)

    TopAppBar(
        title = { Text("Readative") },
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Image(
                    modifier = Modifier.size(42.dp),
                    painter = painterResource(R.drawable.ic_logotype),
                    contentDescription = null
                )
            }
        },
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Outlined.GridView,
                    contentDescription = null
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Outlined.Tune,
                    contentDescription = null
                )
            }
            IconButton(onClick = { navController.navigate(ReadativeScreen.SearchScreen.route) }) {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = null
                )
            }
        }
    )
}

@Composable
fun ReadativeBottomNav(
    navController: NavController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    BottomNavigation() {
        BottomNavigationItem(
            selected = currentRoute == ReadativeScreen.LibraryScreen.route,
            icon = {
                Icon(
                    imageVector = if (currentRoute == ReadativeScreen.LibraryScreen.route) {
                        ReadativeScreen.LibraryScreen.iconActive
                    } else {
                        ReadativeScreen.LibraryScreen.icon
                    },
                    contentDescription = ReadativeScreen.LibraryScreen.name
                )
            },
            label = { Text(text = ReadativeScreen.LibraryScreen.name) },
            onClick = { navController.navigate(ReadativeScreen.LibraryScreen.route) }
        )
        BottomNavigationItem(
            selected = currentRoute == ReadativeScreen.StorageScreen.route,
            icon = {
                Icon(
                    imageVector = if (currentRoute == ReadativeScreen.StorageScreen.route) {
                        ReadativeScreen.StorageScreen.iconActive
                    } else {
                        ReadativeScreen.StorageScreen.icon
                    },
                    contentDescription = ReadativeScreen.StorageScreen.name
                )
            },
            label = { Text(text = ReadativeScreen.StorageScreen.name) },
            onClick = { navController.navigate(ReadativeScreen.StorageScreen.route) }
        )
        BottomNavigationItem(
            selected = currentRoute == ReadativeScreen.ProfileScreen.route,
            icon = {
                Icon(
                    imageVector = if (currentRoute == ReadativeScreen.ProfileScreen.route) {
                        ReadativeScreen.ProfileScreen.iconActive
                    } else {
                        ReadativeScreen.ProfileScreen.icon
                    },
                    contentDescription = ReadativeScreen.ProfileScreen.name
                )
            },
            label = { Text(text = ReadativeScreen.ProfileScreen.name) },
            onClick = { navController.navigate(ReadativeScreen.ProfileScreen.route) }
        )
    }
}