package com.checkout.frames.cvvinputfield

import android.content.Context
import com.checkout.base.model.Environment
import com.checkout.frames.cvvinputfield.api.CVVComponentApi
import com.checkout.frames.cvvinputfield.api.InternalCVVComponentApi

/**
 * CVVComponentApiFactory provides [CVVComponentApi]
 */
public object CVVComponentApiFactory {

    /**
     * Creates [CVVComponentApi]
     *
     * @param publicKey - used for client-side authentication in the SDK
     * @param environment - [Environment] represent the environment for tokenization
     * @param context - represent the application context
     */
    @JvmStatic
    public fun create(
        publicKey: String,
        environment: Environment,
        context: Context,
    ): CVVComponentApi {
        return InternalCVVComponentApi(publicKey, environment, context)
    }
}
