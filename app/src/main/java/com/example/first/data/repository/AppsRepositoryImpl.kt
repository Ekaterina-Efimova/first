package com.example.first.data.repository

import com.example.first.data.api.AppsApi
import com.example.first.data.mapper.toDomain
import com.example.first.domain.model.App
import com.example.first.domain.repository.AppsRepository
import javax.inject.Inject

class AppsRepositoryImpl @Inject constructor(
    private val api: AppsApi
) : AppsRepository {

    override suspend fun getApps(): List<App> {
        return api.getCatalog().map { it.toDomain() }
    }
}