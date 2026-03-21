package com.abhi41.receipe.presentation.new_recipe_ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// --- Theme Colors ---
val AppBackground = Color(0xFF1B140D)
val CardSurface = Color(0xFF2C1F15)
val PrimaryOrange = Color(0xFFF08C21)
val TextMain = Color(0xFFEAE2D9)
val TextSub = Color(0xFF9F9489)
val TagBackground = Color(0xFFF08C21).copy(alpha = 0.8f)

@Composable
fun CulinaShareTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = darkColorScheme(
            background = AppBackground,
            surface = CardSurface,
            primary = PrimaryOrange,
            onSurface = TextMain
        ),
        content = content
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipesScreen() {
    Scaffold(
        bottomBar = { BottomNavBar() },
        containerColor = AppBackground
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { TopHeader() }
            item { SearchBar() }
            item { CategoryFilters() }
            item { FoodJokeCard() }

            // Recipe List
            items(1) {
                RecipeCard(
                    title = "Mediterranean Buddha Bowl",
                    description = "A colorful mix of quinoa, fresh chickpeas, cucumbers, and a zesty lemon tahini dressing for...",
                    rating = "4.8",
                    reviews = "124",
                    time = "15 MIN",
                    tag = "VEGAN"
                )
            }
        }

        // Floating Action Button (Filter)
        Box(modifier = Modifier.fillMaxSize()) {
            SmallFloatingActionButton(
                onClick = { },
                containerColor = PrimaryOrange,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 100.dp, end = 16.dp)
                    .size(56.dp),
                shape = CircleShape
            ) {
                Icon(Icons.Default.List, contentDescription = "Filter", tint = Color.White)
            }
        }
    }
}

@Composable
fun TopHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            shape = CircleShape,
            color = CardSurface,
            modifier = Modifier.size(45.dp)
        ) {
            Icon(Icons.Default.Menu, contentDescription = null, tint = PrimaryOrange, modifier = Modifier.padding(10.dp))
        }
        Text("Recipes", style = MaterialTheme.typography.headlineSmall, color = TextMain, fontWeight = FontWeight.Bold)
        Image(
            painter = painterResource(id = android.R.drawable.presence_away), // Placeholder for profile
            contentDescription = "Profile",
            modifier = Modifier
                .size(45.dp)
                .clip(CircleShape)
                .background(Color.Gray)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar() {
    TextField(
        value = "",
        onValueChange = {},
        placeholder = { Text("Search recipes...", color = TextSub) },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = PrimaryOrange) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = CardSurface,
            unfocusedContainerColor = CardSurface,
            disabledContainerColor = CardSurface,
            cursorColor = PrimaryOrange,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        )
    )
}

@Composable
fun CategoryFilters() {
    val categories = listOf("All Meals", "Breakfast", "Lunch", "Dinner")
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(categories) { category ->
            val isSelected = category == "All Meals"
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = if (isSelected) PrimaryOrange else CardSurface,
                modifier = Modifier.clickable { }
            ) {
                Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(category, color = if (isSelected) Color.White else TextSub)
                    if (category != "All Meals") {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = TextSub)
                    }
                }
            }
        }
    }
}

@Composable
fun FoodJokeCard() {
    Card(
        colors = CardDefaults.cardColors(containerColor = CardSurface),
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Surface(shape = CircleShape, color = AppBackground, modifier = Modifier.size(50.dp)) {
                Icon(Icons.Default.Face, contentDescription = null, tint = PrimaryOrange, modifier = Modifier.padding(12.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text("FOOD JOKE OF THE DAY", color = PrimaryOrange, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Text(
                    "\"Why did the tomato turn red? Because it saw the salad dressing!\"",
                    color = TextMain,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun RecipeCard(title: String, description: String, rating: String, reviews: String, time: String, tag: String) {
    Card(
        colors = CardDefaults.cardColors(containerColor = CardSurface),
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Box {
                // Placeholder for Recipe Image
                Surface(modifier = Modifier.fillMaxWidth().height(200.dp), color = Color.Gray) {}

                // Tags Overlay
                Row(modifier = Modifier.align(Alignment.BottomStart).padding(12.dp)) {
                    Badge(containerColor = AppBackground.copy(alpha = 0.7f), modifier = Modifier.padding(end = 8.dp)) {
                        Text(tag, color = Color.White, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                    }
                    Badge(containerColor = PrimaryOrange) {
                        Text(time, color = Color.White, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                    }
                }

                // Favorite Button
                IconButton(
                    onClick = { },
                    modifier = Modifier.align(Alignment.TopEnd).padding(8.dp).background(Color.Black.copy(alpha = 0.3f), CircleShape)
                ) {
                    Icon(Icons.Default.Favorite, contentDescription = null, tint = PrimaryOrange)
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Text(title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Text(description, color = TextSub, maxLines = 2, overflow = TextOverflow.Ellipsis, modifier = Modifier.padding(vertical = 8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Star, contentDescription = null, tint = PrimaryOrange, modifier = Modifier.size(18.dp))
                        Text(" $rating", fontWeight = FontWeight.Bold)
                        Text(" ($reviews reviews)", color = TextSub, fontSize = 12.sp)
                    }
                    TextButton(onClick = { }) {
                        Text("View Recipe", color = PrimaryOrange)
                        Icon(Icons.Default.KeyboardArrowRight, contentDescription = null, tint = PrimaryOrange)
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavBar() {
    NavigationBar(containerColor = AppBackground, tonalElevation = 8.dp) {
        NavigationBarItem(
            selected = true,
            onClick = {},
            icon = { Icon(Icons.Default.List, contentDescription = null) },
            label = { Text("RECIPES") },
            colors = NavigationBarItemDefaults.colors(selectedIconColor = PrimaryOrange, indicatorColor = Color.Transparent)
        )
        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = { Icon(Icons.Outlined.FavoriteBorder, contentDescription = null) },
            label = { Text("FAVORITES") }
        )
        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = { Icon(Icons.Default.Face, contentDescription = null) },
            label = { Text("JOKES") }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRecipes() {
    CulinaShareTheme {
        RecipesScreen()
    }
}