package com.example.first.data.repository

import com.example.first.data.dto.AppDto
import com.example.first.data.mapper.toDomain
import com.example.first.domain.model.App
import com.example.first.domain.repository.AppsRepository
import javax.inject.Inject // <-- Добавить импорт

class AppsRepositoryImpl @Inject constructor() : AppsRepository {

    private val hardcodedApps = listOf(
        AppDto("VK", "Социальная сеть", "Социальные", "ic_launcher_foreground"),
        AppDto("Telegram", "Мессенджер", "Общение", "ic_launcher_foreground"),
        AppDto("YouTube", "Видеохостинг", "Развлечения", "ic_launcher_foreground"),
        AppDto("Spotify", "Музыкальный сервис", "Музыка", "ic_launcher_foreground"),
        AppDto("Notion", "Заметки и задачи", "Продуктивность", "ic_launcher_foreground"),
        AppDto("Duolingo", "Изучение языков", "Образование", "ic_launcher_foreground"),
        AppDto("Google Maps", "Карты и навигация", "Навигация", "ic_launcher_foreground")
    )

    override fun getApps(): List<App> = hardcodedApps.map { it.toDomain() }

}