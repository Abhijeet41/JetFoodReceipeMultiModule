package com.abhi41.receipe.presentation.recipeDetail.tabs

import android.view.ViewGroup
import android.webkit.WebView
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun InstructionScreen(sourceUrl: String) {
    Scaffold { innerPadding ->
        WebviewContent(sourceUrl,  modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun WebviewContent(sourceUrl: String, modifier: Modifier, ) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                settings.javaScriptEnabled = true
                loadUrl(sourceUrl)
            }
        }, update = {
            it.loadUrl(sourceUrl)
        }

    )
}

