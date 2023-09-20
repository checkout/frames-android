package com.checkout.frames.cvvcomponent

import android.content.Context
import com.checkout.base.model.Environment
import com.checkout.frames.cvvcomponent.api.CVVComponentApi
import com.checkout.frames.cvvcomponent.api.CVVComponentApiClient

public object CVVComponentFactory {

    /**
     * Creates [CVVComponentApi]
     *
     * @param publicKey - used for client-side authentication in the SDK
     * @param context - represent the application context
     * @param environment - [Environment] represent the environment for tokenization
     */
    @JvmStatic
    public fun create(
        publicKey: String,
        environment: Environment,
        context: Context,
    ): CVVComponentApi {
        return CVVComponentApiClient(publicKey, environment, context)
    }
}
