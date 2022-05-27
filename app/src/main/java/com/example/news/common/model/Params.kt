package com.example.news.common.model

import com.example.news.domain.news.model.SortBy
import com.example.news.domain.news.model.getSources
import com.example.news.common.DateExtension.SERVER_DATE_FORMAT
import com.example.news.common.DateExtension.getDateString
import com.example.news.common.DateExtension.getMinDate
import java.util.*

data class Params(
    val sortBy: String? = null,
    val sources: String? = null,
    val from: String? = null,
    val to: String? = null
)

fun defaultParams() = Params(
    SortBy.publishedAt.invoke(),
    getSources().map { it.id }.toList().joinToString(),
    getDateString(getMinDate(), SERVER_DATE_FORMAT), // min date for free account
    getDateString(Date(), SERVER_DATE_FORMAT)
)