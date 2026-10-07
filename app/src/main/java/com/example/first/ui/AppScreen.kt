package com.example.first.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.first.R
import com.example.first.ui.components.AppBar
import com.example.first.ui.components.AppCard
import com.example.first.ui.model.AppItem

// Хардкод данных
private val sampleApps = listOf(
    AppItem(R.mipmap.ic_launcher, "VK", "Социальная сеть", "Социальные"),
    AppItem(R.mipmap.ic_launcher, "Telegram", "Мессенджер", "Общение"),
    AppItem(R.mipmap.ic_launcher, "YouTube", "Видеохостинг", "Развлечения"),
    AppItem(R.mipmap.ic_launcher, "Spotify", "Музыкальный сервис", "Музыка"),
    AppItem(R.mipmap.ic_launcher, "Notion", "Заметки и задачи", "Продуктивность"),
    AppItem(R.mipmap.ic_launcher, "Duolingo", "Изучение языков", "Образование"),
    AppItem(R.mipmap.ic_launcher, "VK", "Социальная сеть", "Социальные"),
    AppItem(R.mipmap.ic_launcher, "Telegram", "Мессенджер", "Общение"),
    AppItem(R.mipmap.ic_launcher, "YouTube", "Видеохостинг", "Развлечения"),
    AppItem(R.mipmap.ic_launcher, "Spotify", "Музыкальный сервис", "Музыка"),
    AppItem(R.mipmap.ic_launcher, "Notion", "Заметки и задачи", "Продуктивность"),
    AppItem(R.mipmap.ic_launcher, "Duolingo", "Изучение языков", "Образование"),
    AppItem(R.mipmap.ic_launcher, "Google Maps", "Карты и навигация", "Навигация")
)

@Composable
fun AppsScreen() {
    Scaffold(
        topBar = { AppBar() }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            items(sampleApps) { app ->
                AppCard(app)
            }
        }
    }
}