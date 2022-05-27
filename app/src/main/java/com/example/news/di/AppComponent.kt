package com.example.news.di

import android.app.Application
import com.example.news.presentation.MainActivity
import com.example.news.presentation.favoriteNews.FavoriteArticlesFragment
import com.example.news.presentation.news.list.NewsListFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DbModule::class, ViewModelModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)

    fun inject(newsListFragment: NewsListFragment)

    fun inject(favoriteArticlesFragment: FavoriteArticlesFragment)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun create(): AppComponent
    }
}