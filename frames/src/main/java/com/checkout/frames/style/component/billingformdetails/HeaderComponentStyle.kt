package com.checkout.frames.style.component.billingformdetails

import com.checkout.frames.style.component.base.ButtonStyle
import com.checkout.frames.style.component.base.ContainerStyle
import com.checkout.frames.style.component.base.TextLabelStyle

public data class HeaderComponentStyle @JvmOverloads constructor(
    val headerTitleStyle: TextLabelStyle = TextLabelStyle(),
    val headerButtonStyle: ButtonStyle = ButtonStyle(),
    val headerContainerStyle: ContainerStyle = ContainerStyle(),
)
