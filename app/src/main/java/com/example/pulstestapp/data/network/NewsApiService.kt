package com.example.pulstestapp.data.network

import retrofit2.http.GET
import retrofit2.http.Query


interface NewsApiService {
    @GET("everything?q=media&sortBy=popularity&pageSize=10&language=ru&apiKey=f42c73c843c04a46a1c098c55f28fab7")
    suspend fun getNewsData(
        @Query("page") page: Int
    ): NewsServerModel
}