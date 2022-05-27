package com.example.news.data.news.local

import com.example.news.domain.news.model.Article
import com.example.news.domain.news.model.Source

fun Article.toArticleDb() = ArticleDB(
    author = author,
    content = content,
    description = description,
    publishedAt = publishedAt,
    title = title,
    url = url,
    urlToImage = urlToImage,
    sourceId = source?.id.orEmpty(),
    sourceName = source?.name.orEmpty(),
    id = id
)

fun ArticleDB.toArticle() = Article(
    author = author,
    content = content,
    description = description,
    publishedAt = publishedAt,
    title = title,
    url = url,
    urlToImage = urlToImage,
    source = Source(sourceId, sourceName),
    id = id
)