package org.example.project.feature.search.doamin.usacases

import org.example.project.feature.search.doamin.models.SearchResult
import org.example.project.feature.search.doamin.repository.ISearchRepository

class SearchProductsUseCase(private val repository: ISearchRepository) {
    suspend operator fun invoke(query: String): List<SearchResult> {
        if (query.isBlank() || query.length < 2) return emptyList()

        // Obtenemos la data de los modelos DTO
        val remoteData = repository.getProductsForSearch()
        val filteredResults = mutableListOf<SearchResult>()

        remoteData.forEach { product ->
            // Convertimos precio_base (String -> Double)
            val precioBase = product.precio_base.toDoubleOrNull() ?: 0.0

            product.variantes.forEach { variant ->
                // Forzamos que 'variant' se comporte como el DTO que tiene 'precio_adicional'
                // Si usas 'VarianteDto' de otro feature, asegúrate de que el campo se llame igual
                val precioExtra = variant.precio_adicional.toDoubleOrNull() ?: 0.0

                val matches = product.nombre.contains(query, ignoreCase = true) ||
                        product.categoria_nombre.contains(query, ignoreCase = true) ||
                        variant.color.contains(query, ignoreCase = true)

                if (matches) {
                    filteredResults.add(
                        SearchResult(
                            productId = product.id,
                            variantId = variant.id,
                            title = "${product.nombre} (${variant.color})",
                            price = precioBase + precioExtra,
                            category = product.categoria_nombre,
                            color = variant.color
                        )
                    )
                }
            }
        }
        return filteredResults
    }
}