package com.example.first.ui

import com.example.first.domain.model.AppDetails
import com.example.first.domain.model.InstallState

sealed interface AppDetailsState {
    data object Loading : AppDetailsState
    data class Content(
        val app: AppDetails,
        val installState: InstallState = InstallState.NotInstalled
    ) : AppDetailsState
    data class Error(val message: String) : AppDetailsState
}