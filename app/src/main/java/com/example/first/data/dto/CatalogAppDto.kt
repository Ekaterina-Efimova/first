package com.example.first.data.dto

import com.google.gson.annotations.SerializedName

data class CatalogAppDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String?,
    @SerializedName("category") val category: String?,
    @SerializedName("iconUrl") val iconUrl: String?
)

data class AppDetailDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String?,
    @SerializedName("developer") val developer: String?,
    @SerializedName("category") val category: String?,
    @SerializedName("ageRating") val ageRating: Int?,
    @SerializedName("size") val size: Float?
)