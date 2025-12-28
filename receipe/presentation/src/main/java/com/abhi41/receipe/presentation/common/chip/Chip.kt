package com.abhi41.receipe.presentation.common.chip

import android.graphics.Color
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abhi41.receipe.presentation.R
import com.abhi41.receipe.ui.theme.SMALL_PADDING
import com.abhi41.receipe.ui.theme.buttonColor

@Composable
fun Chip(
    modifier: Modifier = Modifier,
    onSelectionChanged: (String) -> Unit = {},
    isSelected: Boolean = false,
    name: String = "Chip"
) {
    Surface(
        modifier = modifier.padding(4.dp),
        shape = RoundedCornerShape(20.dp),
        color = if (isSelected) colorResource(R.color.colorAccent) else colorResource(R.color.darkGray),
    ) {
        Row(
            modifier = Modifier.toggleable(
                value = isSelected,
                onValueChange = {
                    onSelectionChanged(name)
                }
            ),
            horizontalArrangement = Arrangement.Center
        ) {
            if (isSelected) {
                Icon(
                    modifier = Modifier.padding(
                        start = SMALL_PADDING,
                        top = SMALL_PADDING,
                        bottom = SMALL_PADDING
                    ),
                    painter = painterResource(id = R.drawable.ic_checkmark),
                    contentDescription = "Chip Icon",
                    tint = colorResource(id = R.color.white)
                )
            } else {
                Box { }
            }
            Text(
                modifier = Modifier.padding(SMALL_PADDING),
                text = name,
                style = MaterialTheme.typography.bodyMedium,
                color = colorResource(R.color.white)
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChipPrev() {
    Chip()
}