package com.nattfall.fouras.repository

import com.nattfall.fouras.R
import com.nattfall.fouras.home.data.ProductData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProductsRepository {

    private val initialItems = mutableListOf(
        ProductData(
            id = "a64ec036-999c-4b1e-a96e-75bb1ceda408",
            name = "Bottle",
            image = R.drawable.product1,
            description = """
                Our versatile Bottle is engineered for life on the go.It's perfect for staying hydrated throughout your day.
                
                Its leak-proof design and comfortable grip make it an essential companion for workouts, commutes, or any adventure
            """.trimIndent(),
            price = 149.99f,
        ),
        ProductData(
            id = "dda87774-9ea8-453d-af7b-d78b3103afc7",
            name = "Container",
            image = R.drawable.product2,
            description = """
                Keep your essentials organized with our multipurpose Container. Featuring a secure, airtight seal, it's ideal for storing food, crafts, or small items. 
                
                Its stackable design optimizes space, bringing order and efficiency to your kitchen, pantry, or workspace.
            """.trimIndent(),
            price = 99.99f,
        ),
        ProductData(
            id = "b59a2e13-a6c8-4330-a916-dc0f223a3006",
            name = "Cup",
            image = R.drawable.product3,
            description = """
                Enjoy your favorite beverages in our stylish and functional Cup.Crafted for comfort and convenience, it's designed for both hot and cold drinks. 
                
                Whether you're savoring your morning coffee or unwinding with an evening tea, this cup elevates your drinking experience.
            """.trimIndent(),
            price = 49.99f,
        ),
    )

    private val _products = MutableStateFlow<List<ProductData>>(initialItems)
    val products = _products.asStateFlow()

    private val _checkout = MutableStateFlow<List<ProductData>>(listOf())
    val checkout = _checkout.asStateFlow()

    fun addToCheckout(product: ProductData) {
        _checkout.update { current ->
            current
                .toMutableList()
                .apply {
                    add(product)
                }
        }
    }

    fun removeItemFromCheckout(product: ProductData) {
        _checkout.update { current ->
            current
                .toMutableList()
                .apply {
                    remove(product)
                }
        }
    }

    fun resetCheckout(){
        _checkout.update { emptyList() }
    }
}