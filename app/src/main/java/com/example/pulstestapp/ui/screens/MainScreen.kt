package com.example.pulstestapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pulstestapp.R
import com.example.pulstestapp.model.ArticleModel
import com.example.pulstestapp.ui.NewsUiState
import com.example.pulstestapp.ui.NewsViewModel
import com.example.pulstestapp.ui.theme.PulsTestAppTheme


@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    newsUiState: NewsUiState,
    newsViewModel: NewsViewModel,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when (newsUiState) {
            is NewsUiState.Success -> SuccessNewsScreen(modifier, newsUiState.articles)
            is NewsUiState.Start -> LoadingErrorNewsScreen(modifier, "STARTT")
            is NewsUiState.Loading -> LoadingErrorNewsScreen(modifier, "LOADING")
            is NewsUiState.Error -> LoadingErrorNewsScreen(modifier, "ERRRORG")
        }
        BottomBar(
            modifier = Modifier.align(Alignment.BottomCenter),
            newsViewModel = newsViewModel,

        )
    }

}


@Composable
fun SuccessNewsScreen(
    modifier: Modifier = Modifier,
    newsItemList: List<ArticleModel>
) {
    LazyColumn(
        modifier = modifier.background(MaterialTheme.colors.background)
    ) {
        items(items = newsItemList) {
            item ->  NewsCardItem(item = item)
        }
    }
}

@Composable
fun NewsCardItem(
    modifier: Modifier = Modifier,
    item: ArticleModel
) {
    Card(elevation = 12.dp, modifier = modifier.padding(16.dp)) {
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
    newsViewModel: NewsViewModel
) {
    Row(
        modifier = modifier
            .background(MaterialTheme.colors.secondary)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,

    ) {
        Button(
            onClick = { newsViewModel.changePage(false) },
        ) {
            Text("Назад")
        }
        Button(
            onClick = { /*TODO get news*/ }) {
            Text("Получить новости")
        }
        Button(
            onClick = { newsViewModel.changePage(true) }) {
            Text("Вперед")
        }
    }
}




//@Preview(showBackground = true)
//@Composable
//fun MainScreenPreview() {
//    PulsTestAppTheme {
//        MainScreen(newsUiState = NewsUiState.Loading, )
//    }
//}



