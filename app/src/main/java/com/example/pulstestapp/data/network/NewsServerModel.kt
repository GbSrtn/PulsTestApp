package com.example.pulstestapp.data.network

import com.example.pulstestapp.model.ArticleModel
import com.example.pulstestapp.util.Mapper

data class NewsServerModel(
    val status: String,
    val totalResults: Int,
    val articles: List<Articles>
) : Mapper<List<ArticleModel>> {
    data class Articles(
        val source: ArticlesSource,
        val author: String?,
        val title: String?,
        val description: String?,
        val url: String?,
        val urlToImage: String?,
        val publishedAt: String?,
        val content: String?
    ) {
        data class ArticlesSource(
            val id: String?,
            val name: String?
        )
    }

    override fun to() = articles.map {
        ArticleModel(
            it.title?: "",
            it.urlToImage ?: "",
            it.description ?: "",
            it.url?: "",
            it.author ?: "",)
    }

}
