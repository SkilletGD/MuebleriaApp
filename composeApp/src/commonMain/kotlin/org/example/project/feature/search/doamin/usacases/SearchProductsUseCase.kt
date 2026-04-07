package org.example.project.feature.search.doamin.usacases

import org.example.project.feature.search.doamin.models.SearchResult
import org.example.project.feature.search.doamin.repository.ISearchRepository

// feature/search/doamin/usacases/SearchProductsUseCase.kt
class SearchProductsUseCase(private val repository: ISearchRepository) {
    suspend operator fun invoke(query: String): List<SearchResult> {
        if (query.isBlank() || query.length < 2) return emptyList()

        return try {
            val remoteData = repository.getProductsForSearch()
            val filteredResults = mutableListOf<SearchResult>()

            remoteData.forEach { product ->
                val precioBase = product.precio_base.toDoubleOrNull() ?: 0.0

                // Solo entramos a las variantes si el PRODUCTO coincide con la búsqueda
                val nameMatches = product.nombre.contains(query, ignoreCase = true) ||
                        product.categoria_nombre.contains(query, ignoreCase = true)

                product.variantes.forEach { variant ->
                    val precioExtra = variant.precio_adicional.toDoubleOrNull() ?: 0.0
                    val colorMatches = variant.color.contains(query, ignoreCase = true)

                    if (nameMatches || colorMatches) {
                        filteredResults.add(
                            SearchResult(
                                productId = product.id,
                                variantId = variant.id, // <--- REVISA ESTE ID EN UN PRINT
                                title = "${product.nombre} (${variant.color})",
                                price = precioBase + precioExtra,
                                category = product.categoria_nombre,
                                color = variant.color
                            )
                        )
                    }
                }
            }
            filteredResults
        } catch (e: Exception) {
            // Aquí puedes loguear el error si lo deseas
            emptyList()
        }
    }
}