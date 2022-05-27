package com.example.news.presentation.favoriteNews.adapter

import com.example.news.R
import com.example.news.databinding.ItemNewsBinding
import com.example.news.domain.news.model.Article
import com.example.news.extensions.loadImage
import com.example.news.presentation.base.Holder

class ItemArticleHolder(
    private val onClick: (Article) -> Unit,
    private val onClickShare: ((String) -> Unit),
    private val onImageClick: ((String) -> Unit)
) :
    Holder<Article, ItemNewsBinding>() {

    override fun bind(binding: ItemNewsBinding, item: Article) {
        with(binding) {
            ivImage.loadImage(item.urlToImage)
            tvTitle.text = item.title
            tvDescription.text = item.description
            tvSource.text = item.source?.name
            tvDate.text = item.publishedAt
            ivAddToFavorite.setImageResource(R.drawable.ic_favorite)
            ivAddToFavorite.setOnClickListener { item.let { onClick.invoke(it) } }
            ivShare.setOnClickListener { onClickShare.invoke(item.url) }
            ivImage.setOnLongClickListener {
                onImageClick.invoke(item.urlToImage)
                true
            }
        }
    }
}