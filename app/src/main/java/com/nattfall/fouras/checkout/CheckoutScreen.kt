package com.nattfall.fouras.checkout

import android.icu.text.DecimalFormat
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nattfall.fouras.R
import com.nattfall.fouras.home.data.ProductData
import com.nattfall.fouras.ui.elements.AppScaffold
import com.nattfall.fouras.ui.elements.ContrastText
import com.nattfall.fouras.ui.elements.ContrastType
import com.nattfall.fouras.ui.elements.Image.Decorative
import com.nattfall.fouras.ui.theme.AppButton
import com.nattfall.fouras.ui.theme.FourasTheme
import com.nattfall.fouras.ui.util.ScreenPreview
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import java.util.UUID

@Composable
fun CheckoutScreen(
    navigateUp: () -> Unit,
    navigateToCheckoutFinalized: () -> Unit,
    viewModel: CheckoutViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.getCheckout()

        viewModel.event.collect { newEvent ->
            when (newEvent) {
                CheckoutViewModel.Event.CheckoutComplete -> {
                    navigateToCheckoutFinalized()
                }
            }
        }
    }

    val state by viewModel.state.collectAsState()

    CheckoutView(
        checkoutItems = state.checkoutProducts,
        onBack = navigateUp,
        onCheckout = viewModel::onConfirm,
    )
}

@Composable
private fun CheckoutView(
    checkoutItems: List<ProductData>,
    onBack: () -> Unit,
    onCheckout: () -> Unit,
) {
    AppScaffold(title = stringResource(R.string.checkout), onBack = onBack) { padding ->
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .padding(horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                checkoutItems.forEach { item ->
                    item {
                        CheckoutProductItem(productData = item)
                    }

                    item {
                        HorizontalDivider()
                    }
                }
            }

            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                HorizontalDivider()

                val sum = DecimalFormat("0.00").format(
                    checkoutItems.sumOf { product ->
                        product.price.toDouble()
                    }
                )

                ContrastText(
                    text = stringResource(R.string.total_price, sum),
                    contrastType = ContrastType.Surface,
                )

                AppButton.PrimaryButton(onClick = onCheckout) {
                    ContrastText(
                        text = stringResource(R.string.confirm, sum),
                        contrastType = ContrastType.Primary,
                    )
                }
            }
        }
    }
}

@Composable
private fun CheckoutProductItem(productData: ProductData) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 100.dp)
    ) {
        Decorative(
            modifier = Modifier.clip(shape = RoundedCornerShape(12.dp)),
            painter = painterResource(productData.image),
        )

        Column(
            modifier = Modifier.padding(start = 12.dp),
            verticalArrangement = Arrangement.Center
        ) {
            ContrastText(
                text = productData.name,
                contrastType = ContrastType.Surface,
            )
            ContrastText(
                text = productData.price.toString(),
                contrastType = ContrastType.Surface,
            )
        }
    }
}

@Preview
@Composable
private fun CheckoutViewPreview() {
    val checkoutProducts = listOf(
        ProductData(
            id = UUID.randomUUID().toString(),
            name = "Bottle",
            image = R.drawable.product1,
            description = "Product description",
            price = 24.99f,
        )
    )

    ScreenPreview {
        CheckoutView(
            onBack = {},
            checkoutItems = checkoutProducts,
            onCheckout = {},
        )
    }
}