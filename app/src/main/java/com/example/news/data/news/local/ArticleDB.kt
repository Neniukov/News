package com.example.news.data.news.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ArticleDB(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val title: String,
    val url: String,
    val urlToImage: String,
    val sourceId: String,
    val sourceName: String
)