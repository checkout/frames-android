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
     * @param baseUrlPrefix -  an optional alphanumeric prefix used to route requests to a specific regional or merchant-specific subdomain (e.g., "msdd"). Must be alphanumeric.
     */
    @JvmStatic
    public fun create(
        publicKey: String,
        environment: Environment,
        context: Context,
        baseUrlPrefix: String? = null,
    ): CVVComponentApi {
        return InternalCVVComponentApi(publicKey, environment, context, baseUrlPrefix)
    }
}
