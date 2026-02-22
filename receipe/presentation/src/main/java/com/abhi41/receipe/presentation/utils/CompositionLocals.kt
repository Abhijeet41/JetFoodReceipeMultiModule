package com.abhi41.receipe.presentation.utils

import androidx.compose.runtime.staticCompositionLocalOf
import coil.ImageLoader

val LocalImageLoader = staticCompositionLocalOf<ImageLoader?> {
    // Provide a default null value and a helpful error message
    error("ImageLoader not provided. Did you forget to wrap your app in CompositionLocalProvider(LocalImageLoader provides imageLoader)?")
}
