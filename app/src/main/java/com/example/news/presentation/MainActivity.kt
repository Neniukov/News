package com.example.news.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.news.R
import com.example.news.appComponent
import com.example.news.databinding.ActivityMainBinding
import com.example.news.presentation.base.BaseFragment
import com.example.news.presentation.favoriteNews.FavoriteArticlesFragment
import com.example.news.presentation.news.list.NewsListFragment


class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBottomMenu()
    }

    private fun initBottomMenu() {
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.Home -> NewsListFragment.newInstance().openFragment()
                R.id.Favorite -> FavoriteArticlesFragment.newInstance().openFragment()
            }
            true
        }
        binding.bottomNavigation.selectedItemId = R.id.Home
    }

    private fun BaseFragment<*>.openFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, this)
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}