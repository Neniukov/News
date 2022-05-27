package com.example.news.domain.news.usecase

import com.example.news.data.news.repository.NewsRepository
import com.example.news.domain.news.model.Article
import javax.inject.Inject

class FavoriteArticleUseCase @Inject constructor(private val repository: NewsRepository) {

    suspend fun addToFavorite(article: Article) = repository.addArticleToFavorite(article)

    suspend fun getFavoriteArticles() = repository.getFavoriteArticles()

    suspend fun deleteFavoriteArticle(article: Article) = repository.deleteFavoriteArticle(article)
}