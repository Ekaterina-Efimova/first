package com.example.first.domain.repository

import com.example.first.domain.model.App

interface AppsRepository {
    suspend fun getApps(): List<App>
    suspend fun getRatingsSequentially(ids: List<String>): Map<String, Float>
    suspend fun getRatingsInParallel(ids: List<String>): Map<String, Float>
}