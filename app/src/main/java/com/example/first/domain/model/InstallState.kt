package com.example.first.domain.model

sealed interface InstallState {
    data object NotInstalled : InstallState
    data class Installing(val progress: Int) : InstallState // 0..100
    data object Installed : InstallState
}