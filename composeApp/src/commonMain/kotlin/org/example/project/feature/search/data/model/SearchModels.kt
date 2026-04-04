package org.example.project.feature.search.data.model

import kotlinx.serialization.Serializable


@Serializable
data class ProductDto(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val precio_base: String,
    val categoria_nombre: String,
    val variantes: List<VariantDto>
)

@Serializable
data class VariantDto(
    val id: Int,
    val color: String,
    val sku: String,
    val precio_adicional: String
)

