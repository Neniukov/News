package com.example.news.di

import android.app.Application
import androidx.room.Room
import com.example.news.data.news.local.ArticleDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule {

    @Singleton
    @Provides
    fun provideArticleDb(application: Application) : ArticleDatabase {
        return Room.databaseBuilder(application, ArticleDatabase::class.java, "article_db").build()
    }
}