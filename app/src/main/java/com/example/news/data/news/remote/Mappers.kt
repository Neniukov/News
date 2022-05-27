package com.example.news.data.news.remote

import com.example.news.data.news.remote.response.ArticleResponse
import com.example.news.data.news.remote.response.SourceResponse
import com.example.news.domain.news.model.Article
import com.example.news.domain.news.model.Source
import com.example.news.common.DateExtension.ARTICLE_DATE_FORMAT
import com.example.news.common.DateExtension.FULL_DATE_FORMAT
import com.example.news.common.DateExtension.formatToAnotherFormat

internal fun ArticleResponse.toArticle(): Article {
    return Article(
        source = this.source?.toSource(),
        title = title,
        url = url.orEmpty(),
        description = description.orEmpty(),
        author = author.orEmpty(),
        urlToImage = urlToImage.orEmpty(),
        publishedAt = publishedAt?.let { date ->
            formatToAnotherFormat(
                date,
                FULL_DATE_FORMAT,
                ARTICLE_DATE_FORMAT
            )
        }.orEmpty(),
        content = content.orEmpty()
    )
}

private fun SourceResponse.toSource(): Source {
    return Source(id = id.orEmpty(), name = name)
}
