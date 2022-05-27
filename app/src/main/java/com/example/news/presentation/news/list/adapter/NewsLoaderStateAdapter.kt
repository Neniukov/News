package com.example.news.presentation.news.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.news.databinding.ItemErrorBinding
import com.example.news.databinding.ItemProgressBinding

class NewsLoaderStateAdapter : LoadStateAdapter<NewsLoaderStateAdapter.ItemViewHolder>() {

    override fun getStateViewType(loadState: LoadState) = when (loadState) {
        is LoadState.NotLoading -> error("Not Loading")
        LoadState.Loading -> PROGRESS
        is LoadState.Error -> ERROR
    }

    override fun onBindViewHolder(holder: ItemViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ItemViewHolder {
        return when (loadState) {
            LoadState.Loading -> ProgressViewHolder(LayoutInflater.from(parent.context), parent)
            is LoadState.Error -> ErrorViewHolder(LayoutInflater.from(parent.context), parent)
            is LoadState.NotLoading -> error("Not Loading")
        }
    }

    private companion object {

        private const val ERROR = 1
        private const val PROGRESS = 0
    }

    abstract class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        abstract fun bind(loadState: LoadState)
    }

    class ProgressViewHolder internal constructor(binding: ItemProgressBinding) : ItemViewHolder(binding.root) {

        override fun bind(loadState: LoadState) = Unit

        companion object {
            operator fun invoke(
                layoutInflater: LayoutInflater,
                parent: ViewGroup? = null,
                attachToRoot: Boolean = false
            ): ProgressViewHolder {
                return ProgressViewHolder(ItemProgressBinding.inflate(layoutInflater, parent, attachToRoot))
            }
        }
    }

    class ErrorViewHolder constructor(private val binding: ItemErrorBinding) : ItemViewHolder(binding.root) {

        override fun bind(loadState: LoadState) {
            require(loadState is LoadState.Error)
            binding.tvErrorMessage.text = loadState.error.localizedMessage
        }

        companion object {

            operator fun invoke(
                layoutInflater: LayoutInflater,
                parent: ViewGroup? = null,
                attachToRoot: Boolean = false
            ): ErrorViewHolder {
                return ErrorViewHolder(
                    ItemErrorBinding.inflate(
                        layoutInflater,
                        parent,
                        attachToRoot
                    )
                )
            }
        }
    }
}