package com.nattfall.fouras.complete

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nattfall.fouras.R
import com.nattfall.fouras.ui.elements.ContrastText
import com.nattfall.fouras.ui.elements.ContrastType
import com.nattfall.fouras.ui.theme.AppButton
import com.nattfall.fouras.ui.theme.FourasTheme
import com.nattfall.fouras.ui.util.ScreenPreview

@Composable
fun CheckoutCompleteScreen(
    onComplete: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CheckoutCompleteViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.event.collect { newEvent ->
            when (newEvent) {
                CheckoutCompleteViewModel.Event.CheckoutComplete -> onComplete()
            }
        }
    }

    CheckoutCompleteView(
        modifier = modifier,
        onCompletePressed = viewModel::checkoutPressed,
    )
}

@Composable
private fun CheckoutCompleteView(
    modifier: Modifier = Modifier,
    onCompletePressed: () -> Unit,
) {
    Surface(modifier = Modifier.fillMaxSize().then(modifier)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(1.dp))

            ContrastText(
                style = MaterialTheme.typography.headlineSmall,
                text = stringResource(R.string.order_complete),
                contrastType = ContrastType.Surface,
            )

            AppButton.PrimaryButton(onClick = onCompletePressed) {
                ContrastText(
                    text = stringResource(R.string.confirm),
                    contrastType = ContrastType.Primary,
                )
            }
        }

    }
}

@Preview
@Composable
private fun CheckoutCompleteViewPreview() {
    FourasTheme(darkTheme = false) {
        CheckoutCompleteView(onCompletePressed = {})
    }
}