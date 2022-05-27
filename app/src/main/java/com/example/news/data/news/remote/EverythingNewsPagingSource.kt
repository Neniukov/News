package com.example.news.data.news.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.news.data.news.remote.api.NewsApi
import com.example.news.data.utils.Constants
import com.example.news.domain.news.model.Article
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import retrofit2.HttpException

class EverythingNewsPagingSource @AssistedInject constructor(
    private val newsService: NewsApi,
    @Assisted(SORT_BY) private val sortBy: String,
    @Assisted(SOURCES) private val sources: String,
    @Assisted(FROM) private val from: String,
    @Assisted(TO) private val to: String
) : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        try {
            val pageNumber = params.key ?: INITIAL_PAGE_NUMBER
            val pageSize = params.loadSize.coerceAtMost(Constants.DEFAULT_PAGE_SIZE)
            val response =
                newsService.everything(
                    page = pageNumber,
                    pageSize = pageSize,
                    sortBy = sortBy,
                    from = from,
                    to = to,
                    sources = sources
                )
            return if (response.isSuccessful) {
                val articles = response.body()!!.articles.map { it.toArticle() }
                val nextPageNumber = if (articles.isEmpty()) null else pageNumber + 1
                val prevPageNumber = if (pageNumber > 1) pageNumber - 1 else null
                LoadResult.Page(articles, prevPageNumber, nextPageNumber)
            } else {
                LoadResult.Error(HttpException(response))
            }
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }

    @AssistedFactory
    interface Factory {

        fun create(
            @Assisted(SORT_BY) sortBy: String?,
            @Assisted(SOURCES) sources: String?,
            @Assisted(FROM) from: String?,
            @Assisted(TO) to: String?,
        ): EverythingNewsPagingSource
    }

    companion object {

        private const val INITIAL_PAGE_NUMBER = 1
        private const val SORT_BY = "sortBy"
        private const val SOURCES = "sources"
        private const val FROM = "from"
        private const val TO = "to"
    }
}