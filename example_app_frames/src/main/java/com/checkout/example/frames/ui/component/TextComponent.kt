package com.checkout.example.frames.ui.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
internal fun TextComponent(
    titleResourceId: Int,
    fontSize: Int,
    textColor: Color,
    fontWeight: FontWeight,
    paddingValues: PaddingValues,
) = Text(
    text = stringResource(titleResourceId),
    fontSize = fontSize.sp,
    color = textColor,
    fontWeight = fontWeight,
    modifier = Modifier
        .padding(paddingValues)
        .fillMaxWidth(),
    textAlign = TextAlign.Start,
)
