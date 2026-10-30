package com.example.first.domain.repository

import com.example.first.domain.model.App

interface AppsRepository {
    fun getApps(): List<App>
}