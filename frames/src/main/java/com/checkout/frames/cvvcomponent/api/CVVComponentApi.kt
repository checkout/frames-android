package com.checkout.frames.cvvcomponent.api

import com.checkout.frames.cvvcomponent.models.CVVComponentConfig

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
