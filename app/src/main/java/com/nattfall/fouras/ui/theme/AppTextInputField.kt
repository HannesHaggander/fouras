package com.nattfall.fouras.ui.theme

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun MyTextInputField(
    modifier: Modifier = Modifier,
    value: String,
    onNewValue: (String) -> Unit,
    label: @Composable (() -> Unit)? = null,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onNewValue,
        label = { Text("Label") },
        modifier = modifier
    )
}