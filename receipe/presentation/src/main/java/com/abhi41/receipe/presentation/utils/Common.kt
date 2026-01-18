package com.abhi41.receipe.presentation.utils

object Common {

    fun convertMinutesInHour(strTime: Int): String {
        val hours = strTime.toFloat() / 60
        var timeDisplay = String.format("%.1f",hours)
        return timeDisplay
    }


}