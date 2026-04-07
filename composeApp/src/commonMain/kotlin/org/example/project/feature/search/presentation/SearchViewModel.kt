package org.example.project.feature.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.feature.search.doamin.repository.ISearchRepository
import org.example.project.feature.search.doamin.usacases.SearchProductsUseCase

class SearchViewModel(
    private val searchUseCase: SearchProductsUseCase,
    private val repository: ISearchRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    // Referencia al trabajo de búsqueda actual para poder cancelarlo
    private var searchJob: Job? = null

    init {
        // Observamos las búsquedas recientes desde DataStore de forma reactiva
        viewModelScope.launch {
            repository.getRecentSearches().collect { list ->
                _uiState.update { it.copy(recentSearches = list) }
            }
        }
    }

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.OnQueryChanged -> {
                _uiState.update { it.copy(query = event.query, error = null) }

                // Cancelamos cualquier búsqueda que estuviera en curso
                searchJob?.cancel()

                if (event.query.isNotEmpty()) {
                    searchJob = viewModelScope.launch {
                        // Esperamos 300ms antes de disparar la búsqueda (Debounce)
                        // Esto evita que la app intente conectar a internet por cada letra
                        delay(300)

                        _uiState.update { it.copy(isLoading = true) }

                        try {
                            val searchResults = searchUseCase(event.query)
                            _uiState.update {
                                it.copy(
                                    results = searchResults,
                                    isLoading = false,
                                    error = null
                                )
                            }
                        } catch (e: Exception) {
                            // Si falla el internet, capturamos el error aquí
                            _uiState.update {
                                it.copy(
                                    results = emptyList(),
                                    isLoading = false,
                                    error = "No se pudo conectar al servidor"
                                )
                            }
                        }
                    }
                } else {
                    _uiState.update {
                        it.copy(results = emptyList(), isLoading = false, error = null)
                    }
                }
            }

            is SearchEvent.OnDeleteRecentSearch -> {
                viewModelScope.launch {
                    repository.deleteRecentSearch(event.query)
                }
            }

            is SearchEvent.OnProductClicked -> {
                // Guardamos en el historial solo si hay texto en el buscador
                if (_uiState.value.query.isNotEmpty()) {
                    viewModelScope.launch {
                        repository.saveRecentSearch(_uiState.value.query)
                    }
                }
            }
        }
    }
}