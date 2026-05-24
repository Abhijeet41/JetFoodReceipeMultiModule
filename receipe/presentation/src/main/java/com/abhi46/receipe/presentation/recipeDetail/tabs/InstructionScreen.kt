package com.abhi46.receipe.presentation.recipeDetail.tabs

import android.view.ViewGroup
import android.webkit.WebView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import java.lang.Exception

@Composable
fun InstructionScreen(sourceUrl: String) {
    WebviewContent(
        sourceUrl = sourceUrl,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    )
}

@Composable
fun WebviewContent(sourceUrl: String, modifier: Modifier) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            try {
                WebView(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    settings.javaScriptEnabled = false
                    loadUrl(sourceUrl)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                WebView(context)
            }
        },
        update = {
            it.loadUrl(sourceUrl)
        }
    )
}