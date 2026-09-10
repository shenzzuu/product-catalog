package com.example.productcatalog.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productcatalog.domain.model.Product
import com.example.productcatalog.domain.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ProductDetailUiState(
    val product: Product? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

class ProductDetailViewModel(
    private val repository: ProductRepository,
    private val productId: Int
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductDetailUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadProduct()
    }

    fun loadProduct() {
        if (productId == -1) {
            _uiState.update { it.copy(error = "Invalid product ID") }
            return
        }
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            repository.getProductDetails(productId)
                .onSuccess { product ->
                    _uiState.update { it.copy(product = product, isLoading = false) }
                }
                .onFailure { e ->
                    _uiState.update { it.copy(error = e.message ?: "Unknown error", isLoading = false) }
                }
        }
    }
}
