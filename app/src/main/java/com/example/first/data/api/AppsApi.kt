package com.example.first.data.api

import com.example.first.data.dto.AppDetailDto
import com.example.first.data.dto.CatalogAppDto
import retrofit2.http.GET
import retrofit2.http.Path

interface AppsApi {

    @GET("catalog")
    suspend fun getCatalog(): List<CatalogAppDto>

    @GET("{id}")
    suspend fun getAppDetail(@Path("id") id: String): AppDetailDto // <-- Изменено на String
}