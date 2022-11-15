package com.example.pulstestapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pulstestapp.R
import com.example.pulstestapp.model.ArticleModel
import com.example.pulstestapp.ui.NewsUiState


@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    newsUiState: NewsUiState,
    onArticlesDetailsClicked: (ArticleModel) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when (newsUiState) {
            is NewsUiState.Success -> SuccessNewsScreen(modifier, newsUiState.articleList, onArticlesDetailsClicked)
            is NewsUiState.Loading -> LoadingErrorNewsScreen(modifier, "Loading...")
            is NewsUiState.Error -> LoadingErrorNewsScreen(modifier, "Error")
        }
    }
}


@Composable
fun SuccessNewsScreen(
    modifier: Modifier = Modifier,
    newsItemList: List<ArticleModel>,
    onArticlesDetailsClicked: (ArticleModel) -> Unit
) {
    LazyColumn(
        modifier = modifier.background(MaterialTheme.colors.background)
    ) {
        items(items = newsItemList) {
            item ->  NewsCardItem(item = item, onArticlesDetailsClicked = onArticlesDetailsClicked)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewsCardItem(
    modifier: Modifier = Modifier,
    item: ArticleModel,
    onArticlesDetailsClicked: (ArticleModel) -> Unit
) {
    Card(
        elevation = 12.dp,
        modifier = modifier.padding(16.dp),
        onClick = {
            onArticlesDetailsClicked(item)
        }
    ) {
        Column{
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(item.imageUrl)
                    .crossfade(true)
                    .build(),
                modifier = modifier.fillMaxWidth(),
                contentDescription = "News Photo",
                contentScale = ContentScale.FillWidth,
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
            )
            Text(text = item.title,
                modifier = modifier.padding(4.dp))
        }
    }
}

@Composable
fun LoadingErrorNewsScreen(modifier: Modifier = Modifier, info: String) {
    Box(
        modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = info,
        )
    }
}


@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    onMoveForward: (Boolean) -> Unit,
    onGetNews: () -> Unit
) {
    Row(
        modifier = modifier
            .background(MaterialTheme.colors.surface)

            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,

    ) {
        Button(
            onClick = { onMoveForward(false) },
        ) {
            Text("Назад")
        }
        Button(
            onClick = { onGetNews()}
        ) {
            Text("Получить новости")
        }
        Button(
            onClick = { onMoveForward(true) },
        ) {
            Text("Вперед")
        }
    }
}




