package com.abhi41.core_network.dtos.food_joke

import com.abhi41.core_network.dtos.receipe.ResultDto
import com.google.gson.annotations.SerializedName

data class FoodRecipeDto(
    @SerializedName("results")
    val results: List<ResultDto>
)
