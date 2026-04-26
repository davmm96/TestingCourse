package com.example.testingcourse.productList.presentation

import com.example.testingcourse.productList.domain.model.Product
import com.example.testingcourse.productList.domain.model.SortOption

sealed class ProductListUiState {
    data object Loading : ProductListUiState()
    data class Error(val message: String) : ProductListUiState()
    data class Success(
        val products: List<Product>,
        val categories: List<String>,
        val selectedCategory: String?,
        val sortOption: SortOption
    ) : ProductListUiState()
}