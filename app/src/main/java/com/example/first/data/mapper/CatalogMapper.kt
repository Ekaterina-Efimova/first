package com.example.first.data.mapper

import com.example.first.data.dto.CatalogAppDto
import com.example.first.domain.model.App

fun CatalogAppDto.toDomain(): App {
    return App(
        name = this.name,
        description = this.description ?: "Описание отсутствует",
        category = this.category ?: "Без категории",
        iconName = "ic_launcher_foreground"
    )
}