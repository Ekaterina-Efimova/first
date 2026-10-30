package com.example.first.domain.model

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