package com.example.library

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.books.BooksScreen

@Composable
fun LibraryScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    var selected by remember { mutableStateOf(0) }
    val titles = listOf("All books", "Reading", "Shelves")

    Column {
        TabRow(selectedTabIndex = selected) {
            titles.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = index == selected,
                    onClick = { selected = index }
                )
            }
        }

        when (selected) {
            0 -> BooksScreen(navController)
        }
    }
}