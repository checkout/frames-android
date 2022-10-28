package com.checkout.frames.utils.constants

import androidx.annotation.ColorLong
import com.checkout.frames.model.Margin
import com.checkout.frames.model.Padding
import com.checkout.frames.model.font.FontWeight

public object PaymentButtonConstants {
    /** Default content color. **/
    @ColorLong
    public val contentColor: Long = 0xFFFFFFFF

    /** Default container color. **/
    @ColorLong
    public val containerColor: Long = 0xFF0B5FF0

    /** Default disabled content color. **/
    @ColorLong
    public val disabledContentColor: Long = 0xFF636363

    /** Default disabled container color. **/
    @ColorLong
    public val disabledContainerColor: Long = 0xFFF0F0F0

    /** Default content padding in dp. **/
    public val contentPadding: Padding = Padding(18)

    /** Default button margin in dp. **/
    public val buttonMargin: Margin = Margin(bottom = 24)

    /** Default pay button title font weight. */
    public val fontWeight: FontWeight = FontWeight.SemiBold
}
