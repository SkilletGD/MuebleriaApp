package org.example.project.feature.search.presentation

sealed class SearchEvent {
    data class OnQueryChanged(val query: String) : SearchEvent()
}