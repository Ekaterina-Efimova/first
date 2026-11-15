package com.example.first.data.repository

import com.example.first.data.api.AppsApi
import com.example.first.data.mapper.toDomain
import com.example.first.domain.model.App
import com.example.first.domain.repository.AppsRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class AppsRepositoryImpl @Inject constructor(
    private val api: AppsApi
) : AppsRepository {

    override suspend fun getApps(): List<App> =
        api.getCatalog().map { it.toDomain() }

    override suspend fun getRatingsSequentially(ids: List<String>): Map<String, Float> {
        val result = LinkedHashMap<String, Float>()
        for (id in ids) {
            result[id] = api.getRating(id)
        }
        return result
    }

    override suspend fun getRatingsInParallel(ids: List<String>): Map<String, Float> =
        coroutineScope {
            ids.map { id -> async { id to api.getRating(id) } }
                .awaitAll()
                .toMap()
        }
}