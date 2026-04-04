package org.example.project.feature.profile.presentation.utils

fun formatFecha(fechaIso: String): String {
    return try {
        // Tomamos solo la parte de la fecha: "2026-03-24"
        val fechaCorta = fechaIso.substringBefore("T")
        val partes = fechaCorta.split("-") // [2026, 03, 24]

        val año = partes[0]
        val mesNum = partes[1]
        val dia = partes[2]

        val mesNombre = when (mesNum) {
            "01" -> "Enero"
            "02" -> "Febrero"
            "03" -> "Marzo"
            "04" -> "Abril"
            "05" -> "Mayo"
            "06" -> "Junio"
            "07" -> "Julio"
            "08" -> "Agosto"
            "09" -> "Septiembre"
            "10" -> "Octubre"
            "11" -> "Noviembre"
            "12" -> "Diciembre"
            else -> mesNum
        }

        "$dia de $mesNombre, $año"
    } catch (e: Exception) {
        fechaIso // Si algo falla, devuelve la original para no tronar la app
    }
}