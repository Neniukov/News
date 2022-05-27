package com.example.news.presentation.news.list

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.news.common.model.Params
import com.example.news.common.model.defaultParams
import com.example.news.domain.news.model.Article
import com.example.news.domain.news.model.getSources
import com.example.news.domain.news.usecase.FavoriteArticleUseCase
import com.example.news.domain.news.usecase.NewsUseCase
import com.example.news.presentation.news.list.widget.FilterSourceDialog
import com.example.news.presentation.news.list.widget.SelectSortNewsDialog
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsListViewModel @Inject constructor(
    private val newsUseCase: NewsUseCase,
    private val favoriteArticleUseCase: FavoriteArticleUseCase
) : ViewModel() {

    private val params = MutableStateFlow(defaultParams())

    private var newPagingSource: PagingSource<*, *>? = null

    val news: StateFlow<PagingData<Article>> = params
        .map(::newPager)
        .flatMapLatest { pager -> pager.flow }
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    private fun newPager(params: Params): Pager<Int, Article> {
        return Pager(PagingConfig(DEFAULT_PAGE_SIZE, enablePlaceholders = false)) {
            newsUseCase(params).also { newPagingSource = it }
        }
    }

    fun addToFavorite(article: Article) = viewModelScope.launch {
        favoriteArticleUseCase.addToFavorite(article)
    }

    fun openSortDialog(manager: FragmentManager) {
        val dialog = SelectSortNewsDialog()
        dialog.onClickTypeSort = { params.tryEmit(params.value.copy(sortBy = it.invoke())) }
        dialog.show(manager, SelectSortNewsDialog::class.java.simpleName)
    }

    fun openFilerDialog(manager: FragmentManager) {
        val dialog = FilterSourceDialog.newInstance(
            params.value.sources ?: getSources().map { it.id }.toList().joinToString(),
            params.value.from, params.value.to
        )
        dialog.selectedSources = { sources, dates ->
            val (fromDate, toDate) = dates
            params.tryEmit(
                params.value.copy(
                    sources = sources.filter { it.selected }.map { it.id }.toList().joinToString(),
                    from = fromDate,
                    to = toDate
                )
            )
        }
        dialog.show(manager, FilterSourceDialog::class.java.simpleName)
    }

    companion object {
        private const val DEFAULT_PAGE_SIZE = 5
    }
}