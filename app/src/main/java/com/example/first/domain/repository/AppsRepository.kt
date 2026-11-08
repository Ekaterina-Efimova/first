package com.example.first.domain.repository

import com.example.first.domain.model.App

interface AppsRepository {
    suspend fun getApps(): List<App>
}