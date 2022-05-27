package com.example.news.data.news.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArticlesResponse(
    @SerialName("status") val status: String,
    @SerialName("totalResults") val totalResults: Int,
    @SerialName("message") val message: String? = null,
    @SerialName("articles") val articles: List<ArticleResponse>,
)