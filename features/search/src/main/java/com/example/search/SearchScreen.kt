package com.example.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.books.component.BookItemCard
import com.example.search.components.SearchBar

@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val state: SearchState = viewModel.viewState.collectAsState().value
    
    Scaffold(
        topBar = {
            SearchBar(
                modifier = Modifier.fillMaxWidth(1f),
                navController = navController,
            ) {
                viewModel.queryEntered(it)
            }
        }
    ) {
        when(state) {
            is SearchState.AwaitingInput -> {}
            is SearchState.Loading -> {
                Box(
                    modifier = Modifier.padding(it).fillMaxSize(1f),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is SearchState.NoResult -> {
                Box(
                    modifier = Modifier.padding(it).fillMaxSize(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Your search did not match any books",
                        style = MaterialTheme.typography.subtitle1
                    )
                }
            }
            is SearchState.Loaded -> {
                LazyColumn(
                    modifier = Modifier.padding(it),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(state.data) {
                        BookItemCard(book = it) {
                            navController.navigate("reading_screen/${it.id}")
                        }
                    }
                }
            }
        }
    }
}