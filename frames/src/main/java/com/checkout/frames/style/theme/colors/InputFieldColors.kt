package com.checkout.frames.style.theme.colors

import androidx.annotation.ColorLong
import com.checkout.frames.utils.constants.BorderConstants
import com.checkout.frames.utils.constants.UnderlineConstants

public data class InputFieldColors @JvmOverloads constructor(
    @ColorLong
    val focusedBorderColor: Long = BorderConstants.focusedBorderColor,
    @ColorLong
    val unfocusedBorderColor: Long = BorderConstants.unfocusedBorderColor,
    @ColorLong
    val disabledBorderColor: Long = BorderConstants.disabledBorderColor,
    @ColorLong
    val errorBorderColor: Long = BorderConstants.errorBorderColor,
    @ColorLong
    val inputFieldBackgroundColor: Long = 0XFF24302D,
    @ColorLong
    val focusedUnderlineColor: Long = UnderlineConstants.focusedUnderlineColor,
    @ColorLong
    val unfocusedUnderlineColor: Long = UnderlineConstants.unfocusedUnderlineColor,
    @ColorLong
    val disabledUnderlineColor: Long = UnderlineConstants.disabledUnderlineColor,
    @ColorLong
    val errorUnderlineColor: Long = UnderlineConstants.errorUnderlineColor,
)
