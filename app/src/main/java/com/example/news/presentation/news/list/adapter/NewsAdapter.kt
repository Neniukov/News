package com.example.news.presentation.news.list.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.news.R
import com.example.news.databinding.ItemNewsBinding
import com.example.news.domain.news.model.Article
import com.example.news.extensions.loadImage

class NewsAdapter(
    context: Context,
    private val onClickFavorite: ((Article) -> Unit),
    private val onClickShare: ((String) -> Unit),
    private val onImageClick: ((String) -> Unit)
) :
    PagingDataAdapter<Article, NewsAdapter.NewsViewHolder>(ArticleDiffItemCallback) {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val itemView: ItemNewsBinding? =
            DataBindingUtil.bind(
                layoutInflater.inflate(R.layout.item_news, parent, false)
            )
        return NewsViewHolder(requireNotNull(itemView))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class NewsViewHolder(private val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article?) {
            with(binding) {
                ivImage.loadImage(article?.urlToImage)
                tvTitle.text = article?.title
                tvDescription.text = article?.description
                tvSource.text = article?.source?.name
                tvDate.text = article?.publishedAt
                ivAddToFavorite.setOnClickListener {
                    ivAddToFavorite.setImageResource(R.drawable.ic_favorite)
                    article?.let { onClickFavorite.invoke(it) }
                }
                ivShare.setOnClickListener { onClickShare.invoke(article?.url.orEmpty()) }
                ivImage.setOnLongClickListener {
                    onImageClick.invoke(article?.urlToImage.orEmpty())
                    true
                }
            }
        }
    }
}

private object ArticleDiffItemCallback : DiffUtil.ItemCallback<Article>() {

    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.title == newItem.title && oldItem.url == newItem.url
    }
}