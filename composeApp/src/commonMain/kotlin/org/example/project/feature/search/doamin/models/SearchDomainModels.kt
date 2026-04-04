package org.example.project.feature.search.doamin.models

data class SearchResult(
    val productId: Int,
    val variantId: Int,
    val title: String,
    val price: Double,
    val category: String,
    val color: String
)