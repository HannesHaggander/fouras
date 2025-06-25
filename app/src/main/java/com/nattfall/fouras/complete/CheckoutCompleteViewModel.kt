package com.nattfall.fouras.complete

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

@HiltViewModel
class CheckoutCompleteViewModel @Inject constructor(): ViewModel() {

    private val _event = Channel<Event>()
    val event = _event.receiveAsFlow()

    fun checkoutPressed() {
        _event.trySend(Event.CheckoutComplete)
    }

    sealed interface Event {
        data object CheckoutComplete : Event
    }
}