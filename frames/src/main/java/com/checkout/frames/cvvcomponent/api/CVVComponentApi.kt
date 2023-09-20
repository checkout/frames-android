package com.checkout.frames.cvvcomponent.api

import com.checkout.frames.cvvcomponent.models.CVVComponentConfig

/**
 * Provides functionality for creating ComponentMediator.
 */
public interface CVVComponentApi {

    /**
     * Creates [ComponentMediator] to load CVV component and hitting the tokenization for it.
     *
     * @param cvvComponentConfig - [CVVComponentConfig] contains CVV component configuration.
     */
    public fun createComponentMediator(cvvComponentConfig: CVVComponentConfig): ComponentMediator
}
