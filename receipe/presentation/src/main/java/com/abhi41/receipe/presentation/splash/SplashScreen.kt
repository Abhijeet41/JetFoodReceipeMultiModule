package com.abhi41.receipe.presentation.splash

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.abhi41.receipe.presentation.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(modifier: Modifier = Modifier, onNavigate: () -> Unit) {
    var startAnimation by remember { mutableStateOf(false) }
    var alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 3
        )
    )
    LaunchedEffect(key1 = Unit) {
        startAnimation = true
        delay(3000)
        onNavigate()
    }

    SplashScreenDesign(alphaAnim.value)
}

@Composable
fun SplashScreenDesign(alphaAnim: Float) {


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.alpha(alpha = alphaAnim),
            painter = painterResource(id = R.drawable.splash_screen),
            contentDescription = "Splash Screen"
        )
    }

}
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SplashScreenPreview() {
    // Show the splash screen fully visible in the preview
    SplashScreenDesign(alphaAnim = 1f)
}