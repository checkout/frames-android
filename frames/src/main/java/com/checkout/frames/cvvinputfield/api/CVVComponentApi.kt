package com.checkout.frames.cvvinputfield.api

import com.checkout.frames.cvvinputfield.models.CVVComponentConfig

/**
 * Provides functionality for creating CVVComponentMediator
 */
public interface CVVComponentApi {
    /**
     * Creates [CVVComponentMediator] to load CVV component and hitting the tokenization for it.
     *
     * @param cvvComponentConfig - [CVVComponentConfig] contains CVV component configuration.
     */
    public fun createComponentMediator(cvvComponentConfig: CVVComponentConfig): CVVComponentMediator
}
