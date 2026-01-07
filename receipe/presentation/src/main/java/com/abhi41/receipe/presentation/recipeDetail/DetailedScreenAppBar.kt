package com.abhi41.receipe.presentation.recipeDetail

import android.content.res.Configuration
import android.graphics.Color
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.abhi41.receipe.presentation.R
import com.abhi41.receipe.presentation.utils.HexToJetpackColor
import com.abhi41.receipe.ui.theme.categoriesIconColor
import com.abhi41.receipe.ui.theme.darkYello
import com.abhi41.receipe.ui.theme.titleColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailedScreenAppBar(
    onBackArrowClicked: () -> Unit,
    onFavoriteClicked: () -> Unit
) {
    var isRecipeSaved by remember {
        mutableStateOf(false)
    }
    TopAppBar(
        title = {
            Text(
                text = "RecipeDetails",
                fontWeight = FontWeight.Bold,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.titleColor
                )
            )
        },
        navigationIcon = {
            AppBarIcon(R.drawable.ic_arrow_back) {
                onBackArrowClicked()
            }
        },
        actions = {
            AppBarIcon(
                icon = R.drawable.ic_favorite,
                isRecipeSaved = isRecipeSaved,
                onClick = {
                    isRecipeSaved = !isRecipeSaved
                    onFavoriteClicked()
                }
            )
        },
    )

}

@Composable
fun AppBarIcon(icon: Int, isRecipeSaved: Boolean = false, onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "Icon",
            tint = if (isRecipeSaved) HexToJetpackColor.getColor(darkYello) else androidx.compose.ui.graphics.Color.White
        )
    }
}

@Preview   (showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DetailedScreenAppBarPrev() {
    DetailedScreenAppBar(
        onBackArrowClicked = {},
        onFavoriteClicked = {}
    )
}