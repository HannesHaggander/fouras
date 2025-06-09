package com.nattfall.fouras.checkout

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nattfall.fouras.home.data.ProductData
import com.nattfall.fouras.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val repository: ProductsRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    private val _event = Channel<Event>()
    val event = _event.receiveAsFlow()

    fun getCheckout() {
        viewModelScope.launch {
            repository
                .checkout
                .collectLatest { checkoutProducts ->
                    _state.update { current ->
                        current.copy(checkoutProducts = checkoutProducts)
                    }
                }
        }
    }

    fun onConfirm() {
        repository.resetCheckout()
        _event.trySend(Event.CheckoutComplete)
    }

    data class State(
        val checkoutProducts: List<ProductData> = emptyList(),
    )

    sealed interface Event {
        data object CheckoutComplete: Event
    }

}