package com.example.search.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.navigation.NavController

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    onTextEntered: (text: String) -> Unit
) {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    TextField(
        modifier = modifier,
        value = searchQuery,
        onValueChange = { searchQuery = it },
        singleLine = true,
        placeholder = {
            Text(text = "Search books...")
        },
        keyboardActions = KeyboardActions { onTextEntered(searchQuery) },
        leadingIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Filled.ArrowBack, null)
            }
        },
        trailingIcon = {
            if (searchQuery.isNotEmpty())
                IconButton(onClick = { searchQuery = "" }) {
                    Icon(Icons.Filled.Clear, null)
                }
            else
                Icon(Icons.Filled.Search, null)
        },
        shape = RectangleShape
    )
}