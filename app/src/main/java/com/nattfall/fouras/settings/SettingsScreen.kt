package com.nattfall.fouras.settings

import android.graphics.Paint.Align
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nattfall.fouras.R
import com.nattfall.fouras.ui.elements.AppScaffold
import com.nattfall.fouras.ui.elements.ContrastText
import com.nattfall.fouras.ui.elements.ContrastType
import com.nattfall.fouras.ui.theme.AppButton
import com.nattfall.fouras.ui.util.ScreenPreview

@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    onLogout: () -> Unit,
) {
    SettingsView(
        onBack = onBack,
        onLogout = onLogout,
    )
}

@Composable
private fun SettingsView(
    onBack: () -> Unit,
    onLogout: () -> Unit,
) {
    AppScaffold(
        title = "Settings",
        onBack = onBack,
    ) { scaffoldPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(scaffoldPadding)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AppButton.PrimaryButton(onLogout) {
                ContrastText(
                    text = stringResource(R.string.logout),
                    contrastType = ContrastType.Primary,
                )
            }
        }
    }
}

@Preview
@Composable
private fun SettingsViewPreview() {
    ScreenPreview {
        SettingsView(
            onBack = {},
            onLogout = {},
        )
    }
}