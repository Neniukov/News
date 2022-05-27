package com.example.news.domain.news.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Source(
    val id: String,
    val name: String,
    var selected: Boolean = false
): Parcelable

fun getSources(selected: Boolean = true) = arrayListOf<Source>().apply {
    add(Source("engadget", "Engadget", selected))
    add(Source("reuters", "Reuters", selected))
    add(Source("techcrunch", "TechCrunch", selected))
    add(Source("the-wall-street-journal", "The Wall Street Journal", selected))
    add(Source("business-insider", "Business Insider", selected))
    add(Source("the-next-web", "The Next Web", selected))
}

fun getSourcesByIds(ids: String): ArrayList<Source> {
    val allSources = getSources(false)
    val idsList = ids.split(",").map { it.trim() }
    idsList.forEach { id ->
        allSources.map { if (it.id == id) it.selected = true }
    }
    return allSources
}