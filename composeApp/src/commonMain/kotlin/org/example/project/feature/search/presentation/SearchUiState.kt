package org.example.project.feature.search.presentation

import org.example.project.feature.search.doamin.models.SearchResult

data class SearchUiState(
    val query: String = "",
    val results: List<SearchResult> = emptyList(),
    val isLoading: Boolean = false
)