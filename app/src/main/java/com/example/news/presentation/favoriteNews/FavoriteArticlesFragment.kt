package com.example.news.presentation.favoriteNews

import android.Manifest
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.news.R
import com.example.news.appComponent
import com.example.news.common.model.DialogData
import com.example.news.databinding.FragmentFavoriteArticlesBinding
import com.example.news.domain.news.model.Article
import com.example.news.extensions.downloadImage
import com.example.news.extensions.launchWhenStarted
import com.example.news.extensions.shareLink
import com.example.news.extensions.showDialog
import com.example.news.presentation.base.BaseAdapter
import com.example.news.presentation.base.BaseFragment
import com.example.news.presentation.base.MainViewModelFactory
import com.example.news.presentation.favoriteNews.adapter.ItemArticleHolder
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class FavoriteArticlesFragment : BaseFragment<FragmentFavoriteArticlesBinding>() {

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    override val layoutRes = R.layout.fragment_favorite_articles
    override val viewModel: FavoriteArticlesViewModel by lazy {
        ViewModelProvider(this, mainViewModelFactory).get(FavoriteArticlesViewModel::class.java)
    }

    private var imageUrl = ""
    private val permissionRequestResult =
        registerForActivityResult(ActivityResultContracts.RequestPermission(), ::checkPermissions)


    private val adapter = BaseAdapter()
        .map(
            R.layout.item_news,
            ItemArticleHolder(::onClickDeleteFavorite, ::onClickShareArticle, ::loadImage)
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        requireContext().appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun initViews() {
        super.initViews()
        binding.rvArticles.adapter = adapter
        viewModel.articles
            .onEach { adapter.itemsLoaded(it) }
            .launchWhenStarted(lifecycleScope)
        viewModel.favoriteArticles()
    }

    private fun onClickDeleteFavorite(article: Article) {
        viewModel.deleteArticle(article)
    }

    private fun onClickShareArticle(url: String) {
        requireContext().shareLink(url)
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
        fun newInstance() = FavoriteArticlesFragment()
    }
}