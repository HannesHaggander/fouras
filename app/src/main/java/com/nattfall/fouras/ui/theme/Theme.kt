package com.nattfall.fouras.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = lightColorScheme(
    primary = Sage,
    onPrimary = Color.Black,
    secondary = BrunswickGreen,
    onSecondary = Color.White,
    tertiary = HunterGreen,
    onTertiary = Color.White,
    background = EerieBlack,
    onBackground = Color.White,
    surface = Onyx,
    onSurface = Color.White,
)

private val LightColorScheme = lightColorScheme(
    primary = FernGreen,
    onPrimary = Color.Black,
    secondary = HunterGreen,
    onSecondary = Color.White,
    tertiary = Sage,
    onTertiary = Color.Black,
    background = Seasalt,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
)

@Composable
fun FourasTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
    )
}