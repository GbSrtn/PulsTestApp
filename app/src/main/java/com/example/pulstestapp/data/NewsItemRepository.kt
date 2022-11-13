package com.example.pulstestapp.data

import com.example.pulstestapp.data.network.NewsApiService
import com.example.pulstestapp.model.ArticleModel


interface NewsItemRepository {
    suspend fun getNewsItemList(page: Int) : List<ArticleModel>
}

class BaseNewsItemRepository(
    private val newsApiService: NewsApiService
) : NewsItemRepository {

    override suspend fun getNewsItemList(page: Int): List<ArticleModel> = newsApiService.getNewsData(page).to()
}