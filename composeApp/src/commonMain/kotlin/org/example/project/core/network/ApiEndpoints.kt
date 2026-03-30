package org.example.project.core.network

object ApiEndpoints {
    const val BASE_URL = "https://muebleria-backend-jujb.onrender.com/" // IP especial para acceder al localhost desde el emulador de Android

    object Auth {
        const val LOGIN = "api/auth/login"
        const val REGISTER = "api/auth/register"
        const val PROFILE = "api/auth/profile"
    }

    object Productos {
        const val LISTAR = "api/productos"
        const val VARIANTES = "api/productos/variantes"
        fun detalle(id: Int) = "api/productos/$id"
    }

    object Carrito {
        const val BASE = "api/carrito"
        fun item(id: Int) = "api/carrito/$id"
    }

    object Pedidos {
        const val CREAR = "api/pedidos"
        const val MIS_PEDIDOS = "api/pedidos/mis-pedidos"
    }

    object Envios {
        fun mapa(id: Int) = "api/envios/$id/mapa"
        fun eventos(id: Int) = "api/envios/$id/eventos"
    }
}