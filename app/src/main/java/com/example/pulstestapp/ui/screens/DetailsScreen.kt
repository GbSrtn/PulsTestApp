package com.example.pulstestapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pulstestapp.R
import com.example.pulstestapp.model.ArticleModel
import com.example.pulstestapp.ui.theme.PulsTestAppTheme

@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    article: ArticleModel,
    onLinkClicked: () -> Unit
) {
    val scrollState = rememberScrollState()

    Box(modifier = modifier
        .fillMaxSize()
        .padding(16.dp)
        .clip(RoundedCornerShape(8.dp))
        .background(MaterialTheme.colors.surface)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(8.dp)
                .verticalScroll(state = scrollState)
        ) {
            Text(
                modifier = modifier.padding(bottom = 4.dp),
                text = article.title,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp

            )
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(article.imageUrl)
                    .crossfade(true)
                    .build(),
                modifier = modifier.fillMaxWidth(),
                contentDescription = "News Photo",
                contentScale = ContentScale.FillWidth,
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
            )
            Text(
                text = article.description,
                modifier.padding(bottom = 16.dp, top = 8.dp)
            )
            Text(
                text = article.author,
                modifier = modifier.align(Alignment.End),
            )
            ClickableText(
                text = buildAnnotatedString {
                    val text = "Ссылка на статью"
                    withStyle(
                        style = SpanStyle(MaterialTheme.colors.onError)
                    ) {
                        append(text)
                    }
                },
                modifier = modifier
                    .align(Alignment.End),
                onClick = { onLinkClicked() },
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DetailsScreenPreview() {
    PulsTestAppTheme {
        DetailsScreen(article = ArticleModel(
            "Очень крутой заголовок",
            "",
            "Описание описания по описанию описания всех описаний",
            "https://developer.android.com/courses",
            "Автор Авторович",), onLinkClicked = {})
    }
}