package com.example.first.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.first.domain.model.App
import com.example.first.domain.repository.AppsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.system.measureTimeMillis

@HiltViewModel
class AppsViewModel @Inject constructor(
    private val repository: AppsRepository
) : ViewModel() {

    var apps by mutableStateOf<List<App>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    var snackbarMessage by mutableStateOf<String?>(null)
        private set

    // id приложения -> рейтинг
    var ratings by mutableStateOf<Map<String, Float>>(emptyMap())
        private set

    var ratingsLoading by mutableStateOf(false)
        private set

    var lastMeasure by mutableStateOf<String?>(null)
        private set

    init {
        loadApps()
    }

    fun loadApps() {
        viewModelScope.launch {
            isLoading = true
            error = null
            try {
                apps = repository.getApps()
            } catch (e: Exception) {
                error = "Ошибка загрузки: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    fun loadRatingsSequentially() {
        viewModelScope.launch {
            ratingsLoading = true
            val ids = apps.map { it.name } // в вашей модели id может быть отдельным полем
            var result: Map<String, Float> = emptyMap()
            val time = measureTimeMillis {
                result = repository.getRatingsSequentially(ids)
            }
            ratings = result
            lastMeasure = "Последовательно: ${time} ms"
            ratingsLoading = false
        }
    }

    fun loadRatingsInParallel() {
        viewModelScope.launch {
            ratingsLoading = true
            val ids = apps.map { it.name }
            var result: Map<String, Float> = emptyMap()
            val time = measureTimeMillis {
                result = repository.getRatingsInParallel(ids)
            }
            ratings = result
            lastMeasure = "Параллельно: ${time} ms"
            ratingsLoading = false
        }
    }

    fun onLogoClicked(appName: String) {
        snackbarMessage = "Вы нажали на логотип $appName"
    }

    fun onSnackbarShown() {
        snackbarMessage = null
    }
}