package com.nattfall.fouras.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

object AppButton {

    @Composable
    fun PrimaryButton(
        onClick: () -> Unit,
        content: @Composable () -> Unit
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .heightIn(min = 48.dp),
            onClick = onClick,
            shape = RoundedCornerShape(percent = 50),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
        ) {
            content()
        }
    }

    @Composable
    fun SecondaryButton(
        onClick: () -> Unit,
        content: @Composable () -> Unit
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .heightIn(min = 48.dp),
            onClick = onClick,
            shape = RoundedCornerShape(percent = 50),
            border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.primary),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.primary,
            ),
        ) {
            content()
        }
    }

}