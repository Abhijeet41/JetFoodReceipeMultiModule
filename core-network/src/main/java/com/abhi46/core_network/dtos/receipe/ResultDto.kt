package com.abhi46.core_network.dtos.receipe

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class ResultDto(
    @SerializedName("aggregateLikes")
    val aggregateLikes: Int,

    @SerializedName("cheap")
    val cheap: Boolean,

    @SerializedName("dairyFree")
    val dairyFree: Boolean,

    @SerializedName("extendedIngredients")
    val extendedIngredients: @RawValue List<ExtendedIngredientDto>,

    @SerializedName("glutenFree")
    val glutenFree: Boolean,

    @SerializedName("id")
    val recipeId: Int,

    @SerializedName("image")
    val image: String,

    @SerializedName("readyInMinutes")
    val readyInMinutes: Int,

    @SerializedName("sourceName")
    val sourceName: String?,

    @SerializedName("sourceUrl")
    val sourceUrl: String,
    @SerializedName("summary")
    val summary: String,

    @SerializedName("title")
    val title: String,
    @SerializedName("vegan")
    val vegan: Boolean,
    @SerializedName("vegetarian")
    val vegetarian: Boolean,
    @SerializedName("veryHealthy")
    val veryHealthy: Boolean,
    ) : Parcelable


@Parcelize
data class ExtendedIngredientDto(
    @SerializedName("amount")
    val amount: Double,
    @SerializedName("consistency")
    val consistency: String,
    @SerializedName("image")
    val image: String?,
    @SerializedName("name")
    val name: String,
    @SerializedName("original")
    val original: String,
    @SerializedName("unit")
    val unit: String
) : Parcelable