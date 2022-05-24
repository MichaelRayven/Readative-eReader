package com.example.readative.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.library.LibraryScreen
import com.example.reading.ReadingScreen
import com.example.storage.StorageScreen
import com.example.theme.R

@Composable
fun Navigation(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = ReadativeScreen.LibraryScreen.route
    ) {
        composable(
            route = ReadativeScreen.LibraryScreen.route
        ) {
            ReadativeSurface(navController = navController) {
                LibraryScreen(
                    navController = navController,
                )
            }
        }
        composable(
            route = ReadativeScreen.StorageScreen.route
        ) {
            ReadativeSurface(navController = navController) {
                StorageScreen(
                    navController = navController
                )
            }
        }
        composable(
            route = ReadativeScreen.ProfileScreen.route
        ) {
            ReadativeSurface(navController = navController) {
            }
        }
        composable(
            route = ReadativeScreen.DetailScreen.route + "/{book_id}"
        ) {
        }
        composable(
            route = ReadativeScreen.ReadingScreen.route + "/{book_id}",
            arguments = listOf(navArgument("book_id") { type = NavType.LongType })
        ) {
            it.arguments?.getLong("book_id")?.let { id -> ReadingScreen(id) }
        }
    }
}