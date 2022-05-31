package com.example.storage

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.House
import androidx.compose.material.icons.filled.KeyboardReturn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.theme.R
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.books.component.BookItemCard
import com.example.storage.component.StorageItemCard
import java.io.File

@Composable
fun StorageScreen(
    navController: NavController,
    viewModel: StorageViewModel = hiltViewModel()
) {
    val state: StorageState = viewModel.viewState.collectAsState().value
    when(state) {
        is StorageState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is StorageState.Loaded -> {
            Column {
                TopAppBar(
                    modifier = Modifier.height(32.dp),
                    title = {
                        Text(
                            text = state.currentLocation,
                            style = MaterialTheme.typography.caption
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            viewModel.folderClicked(state.currentLocation.substringBeforeLast(File.separator))
                        }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.storage_back)
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            viewModel.homeClicked()
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Home,
                                contentDescription = stringResource(R.string.storage_back)
                            )
                        }
                    }
                )
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(state.folders) {
                        StorageItemCard(file = it) {
                            viewModel.folderClicked(state.currentLocation + File.separator + it.name)
                        }
                    }
                    items(state.archives) {
                        StorageItemCard(file = it) {
                            viewModel.archiveClicked(state.currentLocation + File.separator + it.name)
                        }
                    }
                    items(state.books) {
                        BookItemCard(book = it)
                        { navController.navigate("reading_screen/${it.id}") }
                    }
                }
            }
        }
        is StorageState.Home -> {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(state.destinations) {
                    StorageItemCard(file = it) { viewModel.folderClicked(it.path) }
                }
            }
        }
        is StorageState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = state.message)
            }
        }
    }
}