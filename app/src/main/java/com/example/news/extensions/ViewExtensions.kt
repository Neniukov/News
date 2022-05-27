package com.example.news.extensions

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide

fun ImageView.loadImage(url: String?, @DrawableRes placeholderRes: Int? = null) {
    if (this.context.isNotAvailable()) return
    Glide.with(this.context)
        .load(url)
        .run { placeholderRes?.let { placeholder(it) } ?: this }
        .into(this)
}