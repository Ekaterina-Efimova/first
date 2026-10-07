package com.example.first.ui.model

import androidx.annotation.DrawableRes

data class AppItem(
    @DrawableRes val iconRes: Int,
    val name: String,
    val description: String,
    val category: String
)