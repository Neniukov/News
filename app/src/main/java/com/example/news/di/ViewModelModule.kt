package com.example.news.di

import androidx.lifecycle.ViewModel
import com.example.news.presentation.favoriteNews.FavoriteArticlesViewModel
import com.example.news.presentation.news.list.NewsListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @ClassKey(NewsListViewModel::class)
    @IntoMap
    abstract fun bindNewsViewModel(viewModel: NewsListViewModel) : ViewModel

    @Binds
    @ClassKey(FavoriteArticlesViewModel::class)
    @IntoMap
    abstract fun bindFavoritesViewModel(viewModel: FavoriteArticlesViewModel) : ViewModel

}