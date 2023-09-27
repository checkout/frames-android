package com.checkout.example.frames.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter

@Composable
fun ClickableImage(
    painter: Painter,
    contentDescription: String,
    onClick: () -> Unit,
    paddingValues: PaddingValues
) {
    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = Modifier
            .defaultMinSize()
            .padding(paddingValues)
            .clickable { onClick() }
    )
}
