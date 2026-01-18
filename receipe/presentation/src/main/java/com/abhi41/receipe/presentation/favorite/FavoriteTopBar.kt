package com.abhi41.receipe.presentation.favorite

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.abhi41.receipe.ui.theme.TXT_MEDIUM_SIZE
import com.abhi41.receipe.ui.theme.topAppBarBackgroundColor
import com.abhi41.receipe.ui.theme.topAppBarContentColor
import kotlinx.coroutines.launch
import kotlin.text.equals

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteTopBar(
    viewModel: FavoritesViewModel,
    state: MutableState<FavoriteState>,
    onDeleteClick: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val backgroundColor = if (state.value.isContextual) {
        Color.DarkGray
    } else {
        MaterialTheme.colorScheme.topAppBarBackgroundColor
    }
    var checkTitleNotNull = state.value.actionModeTitle.equals("")

    TopAppBar(
        title = {  //if checkTitleNotNull is empty show Favorite as title else show actionModeTitle
            Text(
                text = if (checkTitleNotNull) "Favorite" else state.value.actionModeTitle,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.topAppBarContentColor
            )
        },
        actions = {
            if (state.value.isContextual) { //if contextual is true show delete icon
                IconButton(onClick = onDeleteClick) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color.White
                    )
                }
            }else{
                NormalActionBarItems(){ //if contextual is false show more icon
                    //delete all items from database
                    coroutineScope.launch {
                        viewModel.deleteAllFavoriteRecipes()
                    }
                }
            }

        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = backgroundColor
        )
    )

}

@Composable
fun NormalActionBarItems(
    onDeleteAllItemClick: () -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }

    IconButton(onClick = { showMenu = !showMenu }) {
        Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "more icon")
    }

    DropdownMenu(
        expanded = showMenu,
        onDismissRequest = { showMenu = false }
    ) {
        DropdownMenuItem(
            onClick = {
                showMenu = false
                onDeleteAllItemClick()
            },
            text = {
                Text(
                    text = "Delete All",
                    fontSize = TXT_MEDIUM_SIZE,
                    color = Color.White
                )
            }
        )
    }
}




