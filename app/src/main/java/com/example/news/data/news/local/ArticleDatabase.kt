package com.example.news.data.news.local

import androidx.room.Database
import androidx.room.RoomDatabase

private const val DB_VERSION = 1

@Database(entities = [ArticleDB::class], version = DB_VERSION)
abstract class ArticleDatabase : RoomDatabase() {

    abstract fun articleDao(): ArticleDao
}