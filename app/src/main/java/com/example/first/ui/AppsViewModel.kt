package com.example.first.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.first.domain.model.App
import com.example.first.domain.repository.AppsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppsViewModel @Inject constructor(private val repository: AppsRepository) : ViewModel() {

    var apps by mutableStateOf<List<App>>(emptyList())
        private set

    var snackbarMessage by mutableStateOf<String?>(null)
        private set

    init {
        apps = repository.getApps()
    }

    fun onLogoClicked(appName: String) {
        snackbarMessage = "Вы нажали на логотип $appName"
    }

    fun onSnackbarShown() {
        snackbarMessage = null
    }
}