package org.example.project.feature.search.presentation

sealed class SearchEvent {
    // Cuando el usuario escribe en el TextField
    data class OnQueryChanged(val query: String) : SearchEvent()

    // Cuando el usuario pulsa la "X" en una búsqueda reciente (para borrarla de DataStore)
    data class OnDeleteRecentSearch(val query: String) : SearchEvent()

    // Cuando el usuario hace clic en un resultado de la lista (para guardar en el historial)
    // Pasamos el ID o el objeto para navegar, pero el evento le avisa al VM que debe guardar
    data class OnProductClicked(val productId: Int) : SearchEvent()
}