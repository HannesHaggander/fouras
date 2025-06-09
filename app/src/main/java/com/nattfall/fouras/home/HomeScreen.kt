package com.nattfall.fouras.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nattfall.fouras.R
import com.nattfall.fouras.home.data.ProductData
import com.nattfall.fouras.ui.theme.AppButton
import com.nattfall.fouras.ui.theme.FourasTheme
import java.util.UUID

@Composable
fun HomeScreen(
    navigateToProductDetail: (id: String) -> Unit,
    navigateToSettings: () -> Unit,
    navigateToCheckout: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.getItems()
    }

    val state by viewModel.state.collectAsState()

    HomeView(
        products = state.product,
        checkoutVisible = state.checkoutVisible,
        onProductClicked = navigateToProductDetail,
        onSettings = navigateToSettings,
        onCheckout = navigateToCheckout,
    )
}

@Composable
private fun HomeView(
    modifier: Modifier = Modifier,
    products: List<ProductData>,
    checkoutVisible: Boolean,
    onProductClicked: (id: String) -> Unit,
    onSettings: () -> Unit,
    onCheckout: () -> Unit,
) {
    Scaffold(modifier = modifier) { scaffoldPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(scaffoldPadding)
                .padding(horizontal = 12.dp)
                .padding(bottom = 12.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                    ) {
                        IconButton(onSettings) {
                            Icon(Icons.Default.Settings, contentDescription = "Settings")
                        }
                    }

                    products.forEachIndexed { index, product ->
                        ProductView(
                            product = product,
                            onClicked = { id ->
                                onProductClicked(id)
                            }
                        )
                    }
                }
            }

            item {
                AnimatedVisibility(
                    visible = checkoutVisible,
                    enter = slideInVertically(tween(250)),
                ) {
                    AppButton.PrimaryButton(onCheckout) {
                        Text("Checkout")
                    }
                }
            }
        }
    }
}

@Composable
private fun ProductView(
    product: ProductData,
    onClicked: (id: String) -> Unit,
) {
    Row(
        modifier = Modifier
            .clickable { onClicked(product.id) }
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(12.dp),
            ),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth(fraction = 0.3f)
                .clip(shape = RoundedCornerShape(8.dp)),
            painter = painterResource(id = product.image),
            contentDescription = null,
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = product.name,
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = product.description,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = "${product.price} ${product.currency}",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Preview
@Composable
private fun HomeViewPreview() {
    val products = listOf(
        ProductData(
            id = UUID.randomUUID().toString(),
            name = "Bottle",
            image = R.drawable.product1,
            description = "Product description",
            price = 24.99f,
        ),
        ProductData(
            id = UUID.randomUUID().toString(),
            name = "Container",
            image = R.drawable.product2,
            description = "Product description",
            price = 9.99f,
        ),
        ProductData(
            id = UUID.randomUUID().toString(),
            name = "Cup",
            image = R.drawable.product3,
            description = "Product description",
            price = 4.99f,
        ),
    )

    FourasTheme {
        Surface {
            HomeView(
                products = products,
                checkoutVisible = true,
                onProductClicked = {},
                onSettings = {},
                onCheckout = {},
            )
        }
    }
}