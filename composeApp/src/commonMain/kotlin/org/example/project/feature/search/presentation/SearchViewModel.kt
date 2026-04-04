package org.example.project.feature.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.feature.search.doamin.usacases.SearchProductsUseCase

class SearchViewModel(private val searchUseCase: SearchProductsUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.OnQueryChanged -> {
                _uiState.update { it.copy(query = event.query, isLoading = true) }
                viewModelScope.launch {
                    val searchResults = searchUseCase(event.query)
                    _uiState.update { it.copy(results = searchResults, isLoading = false) }
                }
            }
        }
    }
}