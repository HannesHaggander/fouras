package com.nattfall.fouras.productdetail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nattfall.fouras.R
import com.nattfall.fouras.home.data.ProductData
import com.nattfall.fouras.ui.elements.AppScaffold
import com.nattfall.fouras.ui.elements.LoadingResourceIndicator
import com.nattfall.fouras.ui.theme.AppButton
import com.nattfall.fouras.ui.theme.FourasTheme
import java.util.UUID

@Composable
fun ProductDetailScreen(
    productId: String,
    onBack: () -> Unit,
    navigateToCheckout: () -> Unit,
    viewModel: ProductDetailViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.getItem(id = productId)
    }

    val state by viewModel.state.collectAsState()

    ProductDetailView(
        productData = state.product,
        onBack = onBack,
        onAddToCart = viewModel::addToCart,
        onRemoveFromCheckout = viewModel::removeFromCart,
        onCheckout = navigateToCheckout,
        isInCheckout = state.isInCheckout,
    )
}

@Composable
private fun ProductDetailView(
    productData: ProductData?,
    isInCheckout: Boolean,
    onBack: () -> Unit,
    onAddToCart: (ProductData) -> Unit,
    onRemoveFromCheckout: (ProductData) -> Unit,
    onCheckout: () -> Unit,
) {
    AppScaffold(
        title = "",
        onBack = onBack,
    ) { scaffoldPadding ->
        productData?.let {
            Column(
                modifier = Modifier
                    .padding(scaffoldPadding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Image(
                        modifier = Modifier.fillMaxWidth(),
                        painter = painterResource(productData.image),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth,
                    )

                    Column(modifier = Modifier.padding(24.dp)) {
                        Text(
                            text = productData.name,
                            style = MaterialTheme.typography.headlineMedium,
                        )

                        Text(text = productData.description)
                    }
                }

                Column(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    AppButton.PrimaryButton(onClick = {
                        if (isInCheckout) {
                            onRemoveFromCheckout(productData)
                        } else {
                            onAddToCart(productData)
                        }
                    }) {
                        if (isInCheckout) {
                            Text(text = "Remove from cart")
                        } else {
                            Text(text = "Add to cart")
                        }
                    }

                    if(isInCheckout){
                        AppButton.SecondaryButton(onClick = onCheckout) {
                            Text(text = "Checkout")
                        }
                    }
                }
            }
        } ?: LoadingResourceIndicator()
    }
}

@Preview
@Composable
private fun ProductDetailViewPreview() {
    val product = ProductData(
        id = "a64ec036-999c-4b1e-a96e-75bb1ceda408",
        name = "Bottle",
        image = R.drawable.product1,
        description = """
                Our versatile bottle is engineered for life on the go. It's perfect for staying hydrated throughout your day.
              
                Its leak-proof design and comfortable grip make it an essential companion for workouts, commutes, or any adventure
            """.trimIndent(),
        price = 149.99f,
    )

    FourasTheme {
        Surface {
            ProductDetailView(
                productData = product,
                onBack = {},
                onCheckout = {},
                onAddToCart = {},
                onRemoveFromCheckout = {},
                isInCheckout = false,
            )
        }
    }
}