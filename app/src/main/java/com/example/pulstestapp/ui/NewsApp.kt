package com.example.pulstestapp.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pulstestapp.ui.screens.BottomBar
import com.example.pulstestapp.ui.screens.DetailsScreen
import com.example.pulstestapp.ui.screens.MainScreen

enum class NewsAppScreen {
    Main,
    Details
}

@Composable
fun NewsApp(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    val newsViewModel: NewsViewModel = viewModel(factory = NewsViewModel.Factory)
    val articleItem by newsViewModel.articleItem.collectAsState()

    Surface(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
    ) {
        NavHost(
            navController = navController,
            startDestination = NewsAppScreen.Main.name,
        ) {
            composable(route = NewsAppScreen.Main.name) {
                val context = LocalContext.current
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                    bottomBar = {
                        BottomBar(
                            onMoveForward = {
                                newsViewModel.changePage(it,context)
                            },
                            onGetNews = {
                                newsViewModel.getNewsList()
                            }
                        )
                    }
                ) { padding ->
                    MainScreen(
                        modifier.padding(padding),
                        newsUiState = newsViewModel.newsUiState,
                        onArticlesDetailsClicked = {
                            newsViewModel.updateArticleItem(it)
                            navController.navigate(NewsAppScreen.Details.name)
                        }
                    )
                }
            }
            composable(route = NewsAppScreen.Details.name) {
                val context = LocalContext.current
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    DetailsScreen(
                        article = articleItem,
                        onLinkClicked = {
                            val intent = Intent(Intent.ACTION_VIEW)
                            intent.data = Uri.parse(articleItem.url)
                            context.startActivity(intent)
                        }
                    )
                }
            }
        }


    }
}