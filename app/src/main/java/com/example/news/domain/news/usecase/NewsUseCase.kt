package com.example.news.domain.news.usecase

import com.example.news.common.model.Params
import com.example.news.data.news.repository.NewsRepository
import javax.inject.Inject

class NewsUseCase @Inject constructor(private val repository: NewsRepository) {

    operator fun invoke(params: Params) = repository.getNews(params)

}