package org.example.project.feature.productdetail.data.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class ProductDetailDto(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    @SerialName("precio_base") val precioBase: String,
    @SerialName("categoria_nombre") val categoriaNombre: String,
    @SerialName("imagen_url") val imagenUrl: String?,
    val variantes: List<VarianteDto> = emptyList()
)

@Serializable
data class VarianteDto(
    val id: Int,
    val color: String,
    val stock: Int,
    @SerialName("precio_adicional") val precioAdicional: String
)