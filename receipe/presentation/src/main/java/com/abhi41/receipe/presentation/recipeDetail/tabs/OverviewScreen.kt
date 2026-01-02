package com.abhi41.receipe.presentation.recipeDetail.tabs

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.abhi41.receipe.domain.models.RecipeResult
import com.abhi41.receipe.presentation.R
import com.abhi41.receipe.ui.theme.SMALL_PADDING
import com.abhi41.receipe.ui.theme.TXT_MEDIUM_SIZE
import com.abhi41.receipe.ui.theme.categoriesBackgroundColor
import com.abhi41.receipe.ui.theme.categoriesIconColor
import com.abhi41.receipe.ui.theme.categoriesSelectedIconColor
import com.abhi41.receipe.ui.theme.descriptionColor
import com.abhi41.receipe.ui.theme.motionLayoutBg
import com.abhi41.receipe.ui.theme.titleColor
import org.jsoup.Jsoup

@OptIn(ExperimentalMotionApi::class)
@Composable
fun OverviewScreen(selectedFoodItem: RecipeResult) {
    val context = LocalContext.current


    // --- FIX 2: Create a NestedScrollConnection ---
    // This connection will listen to scroll events and update the progress.

    val motionScene = remember {
        context.resources
            .openRawResource(R.raw.overview_motion_img)
            .readBytes()
            .decodeToString()
    }
    var animateButton by remember { mutableStateOf(false) }
    val buttonAnimationProgress by animateFloatAsState(
        targetValue = if (animateButton) 1f else 0f,
        animationSpec = tween(1000)
    )

    Scaffold (){innerPadding ->

        MotionLayout(
            motionScene = MotionScene(content = motionScene),
            progress = buttonAnimationProgress,
            modifier = Modifier
                .fillMaxWidth()
               // .padding(innerPadding)
                .wrapContentHeight()
                .background(MaterialTheme.colorScheme.motionLayoutBg)

        ) {
            val recipeImg = rememberImagePainter(selectedFoodItem.image) {
                crossfade(600)
                error(R.drawable.ic_error_placeholder)
            }
            ImageSection(
                recipeImg,
                selectedFoodItem.aggregateLikes,
                selectedFoodItem.readyInMinutes
            )
            Spacer(modifier = Modifier.height(SMALL_PADDING))
            TitleAndCategorySection(
                selectedItem = selectedFoodItem,
                onClick = {
                    animateButton = !animateButton
                }
            )
            Spacer(modifier = Modifier.height(SMALL_PADDING))
            DescriptionSection(
                summary = selectedFoodItem.summary
            )
        }
    }

}

@Composable
fun ImageSection(
    recipeImg: ImagePainter,
    aggregateLikes: Int? = 1225,
    readyInMinutes: Int? = 40
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .layoutId("imgFood"),
        contentAlignment = Alignment.BottomEnd
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            painter = recipeImg,
            contentDescription = "Food Image",
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color(0x80000000),
                        )
                    )
                )
        )
        Row(
            modifier = Modifier
                .padding(SMALL_PADDING),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            RowLikesAndTimeInfo(text = aggregateLikes.toString(), icon = R.drawable.ic_heart)
            RowLikesAndTimeInfo(text = readyInMinutes.toString(), icon = R.drawable.ic_clock)
        }

    }
}

@Composable
fun RowLikesAndTimeInfo(text: String, icon: Int) {
    Column(
        modifier = Modifier.padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "Heart Icon",
            tint = Color.White
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = text,
            color = Color.White
        )
    }
}

@Composable
fun TitleAndCategorySection(selectedItem: RecipeResult, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .layoutId("title_and_category")
            .clickable { onClick() }
            .background(color = MaterialTheme.colorScheme.categoriesBackgroundColor)
            .padding(SMALL_PADDING),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(
            text = selectedItem.title,
            color = MaterialTheme.colorScheme.titleColor,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            fontWeight = FontWeight.ExtraBold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(SMALL_PADDING))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            ColumnCategory(selectedItem)
        }


    }
}

@Composable
fun ColumnCategory(selectedItem: RecipeResult?) {
    val isVegan = selectedItem?.vegan ?: false
    val isVegetarian = selectedItem?.vegetarian ?: false
    val isGlutenFree = selectedItem?.glutenFree ?: false
    val isDairyFree = selectedItem?.dairyFree ?: false
    val isVeryHealthy = selectedItem?.veryHealthy ?: false
    val isCheap = selectedItem?.cheap ?: false

    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        RowCategories(
            icon = R.drawable.ic_checkmark,
            text = "Vegitarian",
            isVegetarian = isVegetarian
        )
        Spacer(modifier = Modifier.height(5.dp))
        RowCategories(
            icon = R.drawable.ic_checkmark,
            text = "Vegan",
            isVegetarian = isVegan
        )
    }

    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        RowCategories(
            icon = R.drawable.ic_checkmark,
            text = "Diary Free",
            isVegetarian = isDairyFree
        )
        Spacer(modifier = Modifier.height(5.dp))
        RowCategories(
            icon = R.drawable.ic_checkmark,
            text = "Gluten Free",
            isVegetarian = isGlutenFree
        )
    }
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        RowCategories(
            icon = R.drawable.ic_checkmark,
            text = "Healthy",
            isVegetarian = isVeryHealthy
        )
        Spacer(modifier = Modifier.height(5.dp))
        RowCategories(
            icon = R.drawable.ic_checkmark,
            text = "Cheap",
            isVegetarian = isCheap
        )
    }


}

@Composable
fun RowCategories(
    icon: Int,
    text: String,
    isVegetarian: Boolean,
) {
    val color = if (isVegetarian) MaterialTheme.colorScheme.categoriesSelectedIconColor
    else MaterialTheme.colorScheme.categoriesIconColor

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "Selected Check Icon",
            tint = color
        )
        Spacer(modifier = Modifier.padding(5.dp))
        Text(
            text = text,
            color = color,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun DescriptionSection(
    summary: String?
) {
    val summary = Jsoup.parse(summary).text()
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState(1))
            .layoutId("description")
            .padding(
                bottom = SMALL_PADDING,
                start = SMALL_PADDING,
                end = SMALL_PADDING,
            )
    ) {
        Text(
            text = summary
                ?: stringResource(R.string.descriptionDemo),
            color = MaterialTheme.colorScheme.descriptionColor,
            fontSize = TXT_MEDIUM_SIZE,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ImageSectionPrev() {
    val recipeImg = rememberImagePainter("https://img.spoonacular.com/recipes/637016-312x231.jpg") {
        crossfade(600)
        error(R.drawable.ic_error_placeholder)
    }
    ImageSection(
        recipeImg,
        aggregateLikes = 1225,
        readyInMinutes = 40
    )
}

@Preview(showBackground = true)
@Composable
private fun RowLikesAndTimeInfoPrev() {
    RowLikesAndTimeInfo("Likes", R.drawable.ic_heart)
}

@Preview(showBackground = true)
@Composable
private fun TitleAndCategorySectionPreview() {
    // 1. Create a mock RecipeResult object to use in the preview.
    val mockRecipe = RecipeResult(
        aggregateLikes = 123,
        cheap = false,
        dairyFree = true,
        extendedIngredients = emptyList(),
        glutenFree = true,
        recipeId = 1,
        image = "",
        readyInMinutes = 30,
        sourceName = "Food Network",
        sourceUrl = "",
        summary = "A delicious recipe.",
        title = "Awesome Healthy Chicken with a Very Long Name to Test Ellipsis",
        vegan = false,
        vegetarian = false,
        veryHealthy = true
    )

    // 2. Call your composable with the mock data and an empty lambda for the click action.
    TitleAndCategorySection(
        selectedItem = mockRecipe,
        onClick = {}
    )
}


@Preview(showBackground = true)
@Composable
fun RowCategoriesPrev() {
    RowCategories(R.drawable.ic_checkmark, "Vegan", true)
}

@Preview(showBackground = true,showSystemUi = true)
@Composable
private fun OverviewScreenPrev() {
    val mockRecipe = RecipeResult(
        aggregateLikes = 123,
        cheap = false,
        dairyFree = true,
        extendedIngredients = emptyList(),
        glutenFree = true,
        recipeId = 1,
        image = "",
        readyInMinutes = 30,
        sourceName = "Food Network",
        sourceUrl = "",
        summary = "A delicious recipe.",
        title = "Awesome Healthy Chicken with a Very Long Name to Test Ellipsis",
        vegan = false,
        vegetarian = false,
        veryHealthy = true
    )
    OverviewScreen(mockRecipe)
}