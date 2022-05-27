package com.example.news.presentation.news.list.widget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.example.news.R
import com.example.news.domain.news.model.SortBy
import com.example.news.databinding.DialogSortNewsBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SelectSortNewsDialog : BottomSheetDialogFragment() {

    var onClickTypeSort: ((SortBy) -> (Unit))? = null

    private val binding: DialogSortNewsBinding get() = requireNotNull(bindingNullable)
    private var bindingNullable: DialogSortNewsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingNullable =
            DataBindingUtil.inflate(inflater, R.layout.dialog_sort_news, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvPopularity.click(SortBy.popularity)
        binding.tvPublished.click(SortBy.publishedAt)
    }

    private fun TextView.click(type: SortBy) {
        setOnClickListener {
            onClickTypeSort?.invoke(type)
            dismiss()
        }
    }
}