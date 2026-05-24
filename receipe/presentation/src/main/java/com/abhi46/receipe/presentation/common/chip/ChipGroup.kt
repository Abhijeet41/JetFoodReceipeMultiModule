package com.abhi46.receipe.presentation.common.chip

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.abhi46.receipe.domain.utils.Diet
import com.abhi46.receipe.domain.utils.DietType
import com.abhi46.receipe.domain.utils.Meal
import com.abhi46.receipe.domain.utils.MealType
import com.abhi46.receipe.ui.theme.SMALL_PADDING


@Composable
fun MealTypeChipGroup(
    modifier: Modifier = Modifier,
    meals: List<Meal> = MealType.getMeals(),
    selectedMeal: Meal? = MealType.getMeals().get(0),
    onSelectedChange: (String) -> Unit = {}
) {
    LazyRow(modifier = modifier.padding(top = SMALL_PADDING)) {
        items(meals) { item ->
            Chip(
                name = item.meal,
                isSelected = selectedMeal == item,
                onSelectionChanged = {
                    onSelectedChange(it)
                }
            )
        }
    }


}

@Composable
fun DietTypeChipGroup(
    modifier: Modifier = Modifier,
    diets: List<Diet> = DietType.getDiets(),
    selectedDiet: Diet? = DietType.getDiets().get(0),
    onSelectedChange: (String) -> Unit = {}
) {
    LazyRow (
        modifier = modifier.padding(top = SMALL_PADDING)
    ){
        items(diets){
            Chip(
                name = it.diet,
                isSelected = selectedDiet == it,
                onSelectionChanged = {
                    onSelectedChange(it)
                }
            )
        }
    }
}

@Preview
@Composable
private fun MealTypeChipGroupPrev() {
    MealTypeChipGroup()
}

@Preview
@Composable
private fun DietTypeChipGroupPrev() {
    DietTypeChipGroup()
}

