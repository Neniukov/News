package com.example.news.extensions

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent

fun Context?.isNotAvailable(): Boolean = when (this) {
    null -> true
    is Application -> false
    is Activity -> (this.isDestroyed or this.isFinishing)
    else -> false
}

fun Context.shareLink(link: String) {
    val intent = Intent().apply {
        action = Intent.ACTION_SEND
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, link)
    }
    val shareIntent = Intent.createChooser(intent, null)
    startActivity(shareIntent)
}