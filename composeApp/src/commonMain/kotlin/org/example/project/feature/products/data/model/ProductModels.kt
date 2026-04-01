package org.example.project.feature.products.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    @SerialName("precio_base") val precioBase: String,
    @SerialName("categoria_nombre") val categoriaNombre: String,
    @SerialName("imagen_url") val imagenUrl: String? = null,
    val variantes: List<VariantDto> = emptyList()
)

@Serializable
data class VariantDto(
    val id: Int,
    val color: String,
    val stock: Int,
    val sku: String,
    @SerialName("precio_adicional") val precioAdicional: String,
    @SerialName("imagen_url") val imagenUrl: String? = null
)