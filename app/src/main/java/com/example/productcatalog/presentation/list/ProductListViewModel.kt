package com.example.productcatalog.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productcatalog.domain.model.Product
import com.example.productcatalog.domain.repository.ProductRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class ProductListUiState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val isPaginating: Boolean = false,
    val error: String? = null,
    val isRefreshing: Boolean = false,
    val hasReachedEnd: Boolean = false,
    val searchQuery: String = ""
)

@OptIn(FlowPreview::class)
class ProductListViewModel(
    private val repository: ProductRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductListUiState())
    val uiState = _uiState.asStateFlow()

    private val searchQueryFlow = MutableStateFlow("")

    init {
        viewModelScope.launch {
            searchQueryFlow
                .debounce(500)
                .collectLatest { query ->
                    loadProducts(isRefresh = true)
                }
        }
    }

    fun onSearchQueryChanged(query: String) {
        // Update UI state immediately so the text field shows what the user types
        _uiState.update { it.copy(searchQuery = query) }
        // Update the debounced flow to trigger the API call after 500ms
        searchQueryFlow.value = query
    }

    fun triggerSearch() {
        loadProducts(isRefresh = true)
    }

    fun loadProducts(isRefresh: Boolean = false) {
        val currentState = _uiState.value
        if (currentState.isLoading || (currentState.isPaginating && !isRefresh)) return
        if (currentState.hasReachedEnd && !isRefresh) return

        viewModelScope.launch {
            val skip = if (isRefresh) 0 else currentState.products.size
            
            if (isRefresh) {
                _uiState.update { it.copy(isRefreshing = true, error = null, hasReachedEnd = false) }
                if (currentState.products.isEmpty()) {
                    _uiState.update { it.copy(isLoading = true, isRefreshing = false) }
                }
            } else {
                _uiState.update { it.copy(isPaginating = true, error = null) }
            }

            val query = _uiState.value.searchQuery
            val result = if (query.isNotBlank()) {
                repository.searchProducts(query, skip)
            } else {
                repository.getProducts(skip)
            }

            result.onSuccess { newProducts ->
                _uiState.update { state ->
                    val products = if (isRefresh) newProducts else state.products + newProducts
                    state.copy(
                        products = products,
                        isLoading = false,
                        isPaginating = false,
                        isRefreshing = false,
                        hasReachedEnd = newProducts.isEmpty() || newProducts.size < 20
                    )
                }
            }.onFailure { e ->
                _uiState.update { state ->
                    state.copy(
                        error = e.message ?: "An unknown error occurred",
                        isLoading = false,
                        isPaginating = false,
                        isRefreshing = false
                    )
                }
            }
        }
    }
}
