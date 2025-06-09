package com.nattfall.fouras.productdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nattfall.fouras.home.data.ProductData
import com.nattfall.fouras.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productsRepository: ProductsRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    fun getItem(id: String) {
        viewModelScope.launch {
            productsRepository
                .products
                .combine(productsRepository.checkout) { products, checkout ->
                    println("Products: $products, Checkout: $checkout, ID: $id")
                    val product = products.find { it.id == id }
                    State(
                        product = product,
                        isInCheckout = product in checkout
                    )
                }
                .collectLatest { newState ->
                    _state.update { newState }
                }
        }
    }

    fun addToCart(productData: ProductData) {
        productsRepository.addToCheckout(productData)
    }

    fun removeFromCart(productData: ProductData) {
        productsRepository.removeItemFromCheckout(productData)
    }

    data class State(
        val product: ProductData? = null,
        val isInCheckout: Boolean = false,
    )

}