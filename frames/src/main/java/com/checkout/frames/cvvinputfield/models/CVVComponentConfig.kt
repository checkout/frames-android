package com.checkout.frames.cvvinputfield.models

import com.checkout.base.model.CardScheme
import com.checkout.frames.cvvinputfield.style.DefaultCVVInputFieldStyle
import com.checkout.frames.style.component.base.InputFieldStyle

/**
 * CVVComponentConfig - It is used to provide CVV component configuration
 *
 * @param cardScheme - to validate entered CVV is valid or not in the SDK
 * @param onCVVValueChange - provides the isEnteredCVVValid for the given card scheme
 * So, based on isEnteredCVVValid value developer can decide to hit the tokenization for CVV component
 * @param cvvInputFieldStyle - [InputFieldStyle] represent the input-field style for CVV Component
 */
public data class CVVComponentConfig @JvmOverloads constructor(
    public val cardScheme: CardScheme = CardScheme.UNKNOWN,
    public val onCVVValueChange: (isEnteredCVVValid: Boolean) -> Unit,
    public var cvvInputFieldStyle: InputFieldStyle = DefaultCVVInputFieldStyle.create(),
)
