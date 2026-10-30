package com.example.first.data.mapper

import com.example.first.data.dto.AppDto
import com.example.first.domain.model.App

fun AppDto.toDomain(): App = App(
        name = this.name,
        description = this.description,
        category = this.category,
        iconName = this.iconName)
