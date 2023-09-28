package com.checkout.frames.cvvinputfield.api

import android.content.Context
import com.checkout.base.model.Environment
import com.checkout.frames.cvvinputfield.models.CVVComponentConfig

internal class InternalCVVComponentApi(
    private val publicKey: String,
    private val environment: Environment,
    private val context: Context,
) : CVVComponentApi {

    override fun createComponentMediator(cvvComponentConfig: CVVComponentConfig) = InternalCVVComponentMediator(
        cvvComponentConfig = cvvComponentConfig, publicKey = publicKey, environment = environment, context = context
    )
}
