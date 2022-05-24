package com.example.readative.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class ReadativeScreen(
    val route: String,
    val name: String,
    val icon: ImageVector,
    val iconActive: ImageVector,
) {
    object LibraryScreen : ReadativeScreen(
        "library_screen",
        "Library",
        Icons.Outlined.Book,
        Icons.Filled.Book
    )
    object StorageScreen : ReadativeScreen(
        "storage_screen",
        "Storage",
        Icons.Outlined.Folder,
        Icons.Filled.Folder
    )
    object ProfileScreen : ReadativeScreen(
        "profile_screen",
        "Profile",
        Icons.Outlined.AccountCircle,
        Icons.Filled.AccountCircle
    )
    object DetailScreen : ReadativeScreen(
        "detail_screen",
        "Info",
        Icons.Outlined.Info,
        Icons.Filled.Info,
    )
    object ReadingScreen : ReadativeScreen(
        "reading_screen",
        "Read",
        Icons.Outlined.ImportContacts,
        Icons.Filled.ImportContacts
    )

    companion object {
        fun fromRoute(route: String?): ReadativeScreen? {
            return ReadativeScreen::class.sealedSubclasses.map {
                it.objectInstance as ReadativeScreen
            }.find { it.route == route }
        }
    }
}