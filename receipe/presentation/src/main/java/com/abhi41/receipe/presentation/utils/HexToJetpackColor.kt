package com.abhi41.receipe.presentation.utils

import androidx.compose.ui.graphics.Color


object HexToJetpackColor {
    fun getColor(colorString: String): Color {
        return Color(android.graphics.Color.parseColor("#" + colorString))
    }
}