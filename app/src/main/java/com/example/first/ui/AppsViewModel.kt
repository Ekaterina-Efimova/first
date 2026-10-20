package com.example.first.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.first.R
import com.example.first.ui.model.AppItem

class AppsViewModel : ViewModel() {

    // Список хранится в ViewModel, поэтому при повороте экрана он не потеряется
    var sampleApps by mutableStateOf(
        listOf(
            AppItem(R.drawable.ic_launcher_foreground, "VK", "Социальная сеть", "Социальные"),
            AppItem(R.drawable.ic_launcher_foreground, "Telegram", "Мессенджер", "Общение"),
            AppItem(R.drawable.ic_launcher_foreground, "YouTube", "Видеохостинг", "Развлечения"),
            AppItem(R.drawable.ic_launcher_foreground, "Spotify", "Музыкальный сервис", "Музыка"),
            AppItem(R.drawable.ic_launcher_foreground, "Notion", "Заметки и задачи", "Продуктивность"),
            AppItem(R.drawable.ic_launcher_foreground, "Duolingo", "Изучение языков", "Образование"),
            AppItem(R.drawable.ic_launcher_foreground, "Google Maps", "Карты и навигация", "Навигация")
        )
    )
        private set

    // Флаг для показа Snackbar (чтобы он не дублировался)
    var snackbarMessage by mutableStateOf<String?>(null)
        private set

    fun onLogoClicked(appName: String) {
        snackbarMessage = "Вы нажали на логотип $appName"
    }

    fun onSnackbarShown() {
        snackbarMessage = null
    }
}