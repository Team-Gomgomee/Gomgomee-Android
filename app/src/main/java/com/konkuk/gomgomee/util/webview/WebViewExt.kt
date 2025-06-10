package com.konkuk.gomgomee.util.webview

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri

fun openWebView(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
    context.startActivity(intent)
}