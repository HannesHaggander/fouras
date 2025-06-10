package com.nattfall.fouras.ui.elements

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag

object Image {
    @Composable
    fun Decorative(
        painter: Painter,
        modifier: Modifier = Modifier,
        alignment: Alignment = Alignment.Center,
        contentScale: ContentScale = ContentScale.Fit,
        alpha: Float = DefaultAlpha,
        colorFilter: ColorFilter? = null,
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = modifier.semantics {
                testTag = "image.decorative"
                role = androidx.compose.ui.semantics.Role.Image
            },
            alignment = alignment,
            contentScale = contentScale,
            alpha = alpha,
            colorFilter = colorFilter,
        )
    }

    @Composable
    fun Informative(
        painter: Painter,
        @StringRes contentDescriptionRes: Int,
        modifier: Modifier = Modifier,
        alignment: Alignment = Alignment.Center,
        contentScale: ContentScale = ContentScale.Fit,
        alpha: Float = DefaultAlpha,
        colorFilter: ColorFilter? = null,
    ) {
        val contentDescription = stringResource(contentDescriptionRes)

        require(contentDescription.isNotBlank()) {
            "Content description resource value cannot be blank"
        }

        Image(
            painter = painter,
            contentDescription = contentDescription,
            modifier = modifier.semantics {
                testTag = "image.informative.{$contentDescription}"
                role = androidx.compose.ui.semantics.Role.Image
            },
            alignment = alignment,
            contentScale = contentScale,
            alpha = alpha,
            colorFilter = colorFilter,
        )
    }
}