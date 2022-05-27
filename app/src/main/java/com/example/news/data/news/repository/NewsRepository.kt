package com.example.news.data.news.repository

import androidx.paging.PagingSource
import com.example.news.common.model.Params
import com.example.news.data.news.local.ArticleDatabase
import com.example.news.data.news.local.toArticle
import com.example.news.data.news.local.toArticleDb
import com.example.news.data.news.remote.EverythingNewsPagingSource
import com.example.news.domain.news.model.Article
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val source: EverythingNewsPagingSource.Factory,
    private val articleDao: ArticleDatabase
) {

    fun getNews(params: Params): PagingSource<Int, Article> {
        return source.create(params.sortBy, params.sources, params.from, params.to)
    }

    suspend fun getFavoriteArticles() =
        articleDao.articleDao().getArticles().map { it.toArticle() }

    suspend fun addArticleToFavorite(article: Article) =
        articleDao.articleDao().insert(article.toArticleDb())

    suspend fun deleteFavoriteArticle(article: Article) =
        articleDao.articleDao().delete(article.toArticleDb())
}