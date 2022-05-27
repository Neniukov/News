package com.example.news.domain.news.model

enum class SortBy {
    popularity, publishedAt;

    fun invoke() = name
}