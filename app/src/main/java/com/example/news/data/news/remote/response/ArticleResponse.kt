package com.example.news.data.news.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArticleResponse(
    @SerialName("source") val source: SourceResponse? = null,
    @SerialName("title") val title: String = "",
    @SerialName("url") val url: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("author") val author: String? = null,
    @SerialName("urlToImage") val urlToImage: String? = null,
    @SerialName("publishedAt") val publishedAt: String? = null,
    @SerialName("content") val content: String? = null,
)
