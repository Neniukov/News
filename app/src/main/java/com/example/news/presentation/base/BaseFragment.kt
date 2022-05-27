package com.example.news.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<ViewBindingType : ViewBinding> : Fragment() {

    protected abstract val layoutRes: Int
    protected abstract val viewModel: ViewModel

    protected val binding: ViewBindingType
        get() = requireNotNull(bindingNullable)
    private var bindingNullable: ViewBindingType? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingNullable = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    protected open fun initViews() = Unit

    override fun onDestroyView() {
        bindingNullable = null
        super.onDestroyView()
    }
}