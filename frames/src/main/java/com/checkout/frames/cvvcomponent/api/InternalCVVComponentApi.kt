package com.checkout.frames.cvvcomponent.api

import android.content.Context
import com.checkout.base.model.Environment
import com.checkout.frames.cvvcomponent.models.CVVComponentConfig

@Suppress("UnusedPrivateMember")
internal class InternalCVVComponentApi(
    private val publicKey: String,
    private val environment: Environment,
    private val context: Context,
) : CVVComponentApi {

    override fun createComponentMediator(cvvComponentConfig: CVVComponentConfig): CVVComponentMediator {
        return InternalCVVComponentMediator(cvvComponentConfig)
    }
}
