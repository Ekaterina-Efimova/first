package com.example.first.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.first.domain.model.AppDetails
import com.example.first.domain.model.Category
import com.example.first.domain.model.InstallState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppDetailsViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow<AppDetailsState>(AppDetailsState.Loading)
    val state: StateFlow<AppDetailsState> = _state.asStateFlow()

    private var installJob: Job? = null

    fun loadApp(appName: String) {
        // Пока хардкод — в реальности тут был бы запрос к API
        val app = AppDetails(
            name = appName,
            developer = "VK Play",
            category = Category.GAME,
            ageRating = 12,
            size = 223.7f,
            description = "Легендарный рейд героев в Фэнтези РПГ..."
        )
        _state.value = AppDetailsState.Content(app)
    }

    fun onInstallClick() {
        val current = _state.value
        if (current !is AppDetailsState.Content) return

        if (current.installState is InstallState.Installing) {
            // Отмена установки
            installJob?.cancel()
            installJob = null
            _state.update { s ->
                if (s is AppDetailsState.Content) s.copy(installState = InstallState.NotInstalled) else s
            }
        } else {
            // Запуск установки
            installJob = viewModelScope.launch {
                try {
                    emulateInstall()
                } catch (e: kotlinx.coroutines.CancellationException) {
                    // Корректно пробрасываем отмену
                    throw e
                }
            }
        }
    }

    private suspend fun emulateInstall() {
        _state.update { s ->
            if (s is AppDetailsState.Content) s.copy(installState = InstallState.Installing(0)) else s
        }

        for (progress in 0..100 step 5) {
            delay(150)
            _state.update { s ->
                if (s is AppDetailsState.Content) {
                    s.copy(installState = InstallState.Installing(progress))
                } else s
            }
        }

        _state.update { s ->
            if (s is AppDetailsState.Content) s.copy(installState = InstallState.Installed) else s
        }
    }

    override fun onCleared() {
        super.onCleared()
        installJob?.cancel()
    }
}