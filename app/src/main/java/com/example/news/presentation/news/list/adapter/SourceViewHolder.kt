package com.example.news.presentation.news.list.adapter

import com.example.news.databinding.ItemSourceBinding
import com.example.news.domain.news.model.Source
import com.example.news.presentation.base.Holder

class SourceViewHolder : Holder<Source, ItemSourceBinding>() {
    override fun bind(binding: ItemSourceBinding, item: Source) {
        with(binding) {
            tvName.text = item.name
            checkbox.isChecked = item.selected
            root.setOnClickListener {
                item.selected = !item.selected
                checkbox.isChecked = item.selected
            }
        }
    }

}