package com.nattfall.fouras.ui.util

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nattfall.fouras.ui.theme.FourasTheme

@Composable
fun ScreenPreview(content: @Composable () -> Unit) {
    FourasTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            content()
        }
    }
}