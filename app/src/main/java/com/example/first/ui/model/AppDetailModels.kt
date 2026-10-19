package com.example.first.ui.model


data class SimilarApp(
    val name: String,
    val iconRes: Int,
    val description: String
)

enum class Category {
    APP, GAME
}

data class AppDetails(
    val name: String,
    val developer: String,
    val category: Category,
    val ageRating: Int,
    val size: Float,
    val description: String
)