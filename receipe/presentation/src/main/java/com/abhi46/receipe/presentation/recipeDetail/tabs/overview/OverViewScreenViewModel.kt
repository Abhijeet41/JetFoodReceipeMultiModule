package com.abhi46.receipe.presentation.recipeDetail.tabs.overview

import androidx.lifecycle.ViewModel
import coil.ImageLoader
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OverViewScreenViewModel @Inject constructor(
    val imageLoader: ImageLoader
) : ViewModel(){

}