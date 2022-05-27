package com.example.news.presentation.favoriteNews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.domain.news.model.Article
import com.example.news.domain.news.usecase.FavoriteArticleUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteArticlesViewModel @Inject constructor(private val favoriteArticleUseCase: FavoriteArticleUseCase) :
    ViewModel() {

    private val _articles = MutableStateFlow(listOf<Article>())
    val articles: StateFlow<List<Article>> = _articles.asStateFlow()

    fun favoriteArticles() = viewModelScope.launch {
        _articles.emit(favoriteArticleUseCase.getFavoriteArticles())
    }

    fun deleteArticle(article: Article) = viewModelScope.launch {
        favoriteArticleUseCase.deleteFavoriteArticle(article)
        _articles.emit(articles.value.filter { it != article })
    }
}

