package com.example.news.data.news.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ArticleDao {

    @Query("SELECT * FROM articleDB")
    suspend fun getArticles(): List<ArticleDB>

    @Insert
    suspend fun insert(article: ArticleDB)

    @Delete
    suspend fun delete(article: ArticleDB)
}