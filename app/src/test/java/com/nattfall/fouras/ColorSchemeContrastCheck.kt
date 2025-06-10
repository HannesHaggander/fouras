package com.nattfall.fouras

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nattfall.fouras.ui.theme.FourasTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.math.max
import kotlin.math.min

@RunWith(AndroidJUnit4::class)
class ColorSchemeContrastCheck {

    companion object {
        private const val MIN_CONTRAST_RATIO = 4.5
        private const val ENHANCED_CONTRAST_RATIO = 7.0
    }

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun lightModeEN301439ContrastCheck() {
        composeTestRule.setContent {
            FourasTheme(darkTheme = false) {
                RunContrastChecks(isDarkMode = false)
            }
        }
    }

    @Test
    fun darkModeEN301439ContrastCheck() {
        composeTestRule.setContent {
            FourasTheme(darkTheme = true) {
                RunContrastChecks(isDarkMode = true)
            }
        }
    }

    @Composable
    private fun RunContrastChecks(isDarkMode: Boolean) {
        with(MaterialTheme.colorScheme) {
            ensureCompliantContrastRatio(
                color1 = primary,
                color2 = onPrimary,
                message = "Primary and onPrimary",
                isDarkMode = isDarkMode,
            )
            ensureCompliantContrastRatio(
                color1 = secondary,
                color2 = onSecondary,
                message = "secondary and onSecondary",
                isDarkMode = isDarkMode,
            )
            ensureCompliantContrastRatio(
                color1 = tertiary,
                color2 = onTertiary,
                message = "tertiary and onTertiary",
                isDarkMode = isDarkMode,
            )
            ensureCompliantContrastRatio(
                color1 = background,
                color2 = onBackground,
                message = "background and onBackground",
                isDarkMode = isDarkMode,
            )
            ensureCompliantContrastRatio(
                color1 = surface,
                color2 = onSurface,
                message = "surface and onSurface",
                isDarkMode = isDarkMode,
            )
        }
    }

    private fun ensureCompliantContrastRatio(
        color1: Color,
        color2: Color,
        message: String,
        isDarkMode: Boolean,
        enhanced: Boolean = false,
    ) {
        // formula as provided by WCAG 2.1
        val luminance1 = color1.luminance().plus(0.05)
        val luminance2 = color2.luminance().plus(0.05)
        val lighterColor = max(luminance1, luminance2)
        val darkerColor = min(luminance1, luminance2)
        val contrast = lighterColor / darkerColor
        val requiredRatio = if (enhanced) ENHANCED_CONTRAST_RATIO else MIN_CONTRAST_RATIO

        require(
            value = contrast >= requiredRatio,
            lazyMessage = {
                "[${if (isDarkMode) "Dark" else "Light"} mode] $message insufficient contrast ($contrast) < $requiredRatio:1, not EN 301 439 compliant"
            }
        )
    }
}