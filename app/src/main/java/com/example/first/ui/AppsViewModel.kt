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

    fun onLogoClicked(appName: String) {
        snackbarMessage = "Вы нажали на логотип $appName"
    }

    fun onSnackbarShown() {
        snackbarMessage = null
    }
}