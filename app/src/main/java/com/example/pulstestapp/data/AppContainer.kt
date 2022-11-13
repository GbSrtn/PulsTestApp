package com.example.pulstestapp.data

import com.example.pulstestapp.data.network.NewsApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val newsItemRepository : NewsItemRepository
}

class BaseAppContainer :AppContainer {
    private val url = "https://newsapi.org/v2/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(url)
        .build()

    private val retrofitService: NewsApiService by lazy {
        retrofit.create(NewsApiService::class.java)
    }

    override val newsItemRepository: NewsItemRepository by lazy {
        BaseNewsItemRepository(retrofitService)
    }
}