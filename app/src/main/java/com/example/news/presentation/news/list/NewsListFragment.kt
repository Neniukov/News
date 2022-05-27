package com.example.news.presentation.news.list

import android.Manifest
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.addRepeatingJob
import androidx.paging.LoadState
import com.example.news.R
import com.example.news.appComponent
import com.example.news.presentation.base.InAppFragmentDialog
import com.example.news.presentation.base.InAppFragmentDialog.Companion.showMessage
import com.example.news.common.model.DialogData
import com.example.news.databinding.FragmentNewsListBinding
import com.example.news.domain.news.model.Article
import com.example.news.extensions.downloadImage
import com.example.news.extensions.shareLink
import com.example.news.extensions.showDialog
import com.example.news.presentation.base.BaseFragment
import com.example.news.presentation.base.MainViewModelFactory
import com.example.news.presentation.news.list.adapter.NewsAdapter
import com.example.news.presentation.news.list.adapter.NewsLoaderStateAdapter
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class NewsListFragment : BaseFragment<FragmentNewsListBinding>() {

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    override val layoutRes = R.layout.fragment_news_list
    override val viewModel: NewsListViewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this, mainViewModelFactory).get(NewsListViewModel::class.java)
    }

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        NewsAdapter(requireContext(), ::onClickFavoriteArticle, ::onClickShareArticle, ::loadImage)
    }

    private var imageUrl = ""

    private val permissionRequestResult =
        registerForActivityResult(ActivityResultContracts.RequestPermission(), ::checkPermissions)

    override fun onCreate(savedInstanceState: Bundle?) {
        requireContext().appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun initViews() {
        super.initViews()
        with(binding) {
            rvArticles.adapter = adapter.withLoadStateHeaderAndFooter(
                header = NewsLoaderStateAdapter(),
                footer = NewsLoaderStateAdapter()
            )
            ivSortBy.setOnClickListener { viewModel.openSortDialog(requireActivity().supportFragmentManager) }
            ivFilter.setOnClickListener { viewModel.openFilerDialog(requireActivity().supportFragmentManager) }
        }
        initAdapterListener()
        addRepeatingJob(Lifecycle.State.CREATED) {
            viewModel.news.collectLatest(adapter::submitData)
        }
    }

    private fun initAdapterListener() {
        adapter.addLoadStateListener { state ->
            with(binding) {
                rvArticles.isVisible = state.refresh != LoadState.Loading
                progress.isVisible = state.refresh == LoadState.Loading
                if (state.refresh is LoadState.Error) {
                    requireActivity().showMessage(
                        (state.refresh as LoadState.Error).error.localizedMessage,
                        InAppFragmentDialog.Companion.Type.Error
                    )
                }
            }
        }
    }

    private fun onClickFavoriteArticle(article: Article) {
        requireActivity().showMessage(
            getString(R.string.success_add_to_favorite),
            InAppFragmentDialog.Companion.Type.Success
        )
        viewModel.addToFavorite(article)
    }

    private fun onClickShareArticle(url: String) {
        context?.shareLink(url)
    }

    private fun loadImage(imageUrl: String) {
        activity?.showDialog(DialogData(R.string.download_image, R.string.ask_download_image)) {
            this.imageUrl = imageUrl
            permissionRequestResult.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

    private fun checkPermissions(hasPermissions: Boolean) {
        if (hasPermissions) activity?.downloadImage(imageUrl)
        else activity?.showDialog(DialogData(R.string.permission, R.string.permission_explanation)) {
            permissionRequestResult.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

    companion object {
        fun newInstance() = NewsListFragment()
    }

}