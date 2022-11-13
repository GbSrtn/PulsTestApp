package com.example.pulstestapp.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.pulstestapp.ui.screens.BottomBar
import com.example.pulstestapp.ui.screens.MainScreen

enum class NewsAppScreen {
    Main,
    Details
}

@Composable
fun NewsApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()




    val newsViewModel: NewsViewModel = viewModel(factory = NewsViewModel.Factory)

    Scaffold(
        modifier = modifier.fillMaxSize(),
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            color = MaterialTheme.colors.background
        ) {
            MainScreen(
                newsUiState = newsViewModel.newsUiState,
                newsViewModel = newsViewModel
            )
        }
    }
}