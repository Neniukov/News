package com.example.news.data.news.remote.api

import com.example.news.data.news.remote.response.ArticlesResponse
import com.example.news.data.utils.Constants.DEFAULT_PAGE_SIZE
import com.example.news.data.utils.Constants.EVERYTHING_END_POINT
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET(EVERYTHING_END_POINT)
    suspend fun everything(
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = DEFAULT_PAGE_SIZE,
        @Query("sources") sources: String? = null,
        @Query("from") from: String? = null,
        @Query("to") to: String? = null,
        @Query("sortBy") sortBy: String? = null,
    ): Response<ArticlesResponse>
}