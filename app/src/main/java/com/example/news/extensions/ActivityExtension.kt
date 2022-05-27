package com.example.news.extensions

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import com.example.news.R
import com.example.news.common.model.DialogData

fun FragmentActivity.showDialog(data: DialogData, action: () -> Unit) {
    AlertDialog.Builder(this)
        .setTitle(getString(data.title))
        .setMessage(getString(data.text))
        .setPositiveButton(getString(R.string.yes)) { dialog, _ ->
            action.invoke()
            dialog.dismiss()
        }
        .setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
        .show()
}

fun FragmentActivity.downloadImage(url: String) {
    val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    val downloadUri = Uri.parse(url)
    val request = DownloadManager.Request(downloadUri).apply {
        setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            .setAllowedOverRoaming(false)
            .setTitle(url.substring(url.lastIndexOf("/") + 1))
            .setDescription("article_image")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                url.substring(url.lastIndexOf("/") + 1)
            )
    }
    downloadManager.enqueue(request)
}