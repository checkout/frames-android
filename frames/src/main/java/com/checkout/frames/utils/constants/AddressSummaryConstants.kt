package com.checkout.frames.utils.constants

import androidx.annotation.ColorLong
import com.checkout.frames.model.Padding

public object AddressSummaryConstants {

    /** Default address summary text padding in dp. **/
    public val addressSummaryTextPadding: Padding = Padding(
        start = 24,
        end = 24,
        bottom = 16,
        top = 16
    )

    /** Default line height of address summary text in dp. **/
    public const val addressSummaryTextLineHeight: Int = 24

    /** Default divider color in address summary section. **/
    @ColorLong
    public val dividerColor: Long = 0xFFD9D9D9

    /** Default margin before summary section in dp. **/
    public const val marginBeforeSummarySection: Int = 8
    /** Default margin after summary section in dp. **/
    public const val marginAfterSummarySection: Int = 8
}
