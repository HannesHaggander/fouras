package com.nattfall.fouras.home

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
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productsRepository: ProductsRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    fun getItems() {
        viewModelScope.launch {
            productsRepository
                .products
                .combine(productsRepository.checkout) { products, checkout ->
                    State(
                        product = products,
                        checkoutVisible = checkout.isNotEmpty(),
                    )
                }
                .collectLatest { newState ->
                    _state.update { newState }
                }
        }
    }


    data class State(
        val product: List<ProductData> = listOf(),
        val checkoutVisible: Boolean = false,
    )

}