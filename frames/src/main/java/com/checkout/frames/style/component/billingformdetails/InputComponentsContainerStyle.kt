package com.checkout.frames.style.component.billingformdetails

import com.checkout.frames.style.component.base.ContainerStyle
import com.checkout.frames.style.component.base.InputComponentStyle

public data class InputComponentsContainerStyle(
    val inputComponentStyleList: LinkedHashMap<String, InputComponentStyle> = linkedMapOf(),
    var containerStyle: ContainerStyle = ContainerStyle()
)
