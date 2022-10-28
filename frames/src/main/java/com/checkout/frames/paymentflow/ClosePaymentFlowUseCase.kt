package com.checkout.frames.paymentflow

import com.checkout.base.usecase.UseCase

internal class ClosePaymentFlowUseCase(
    private val onClose: () -> Unit
) : UseCase<Unit, Unit> {

    override fun execute(data: Unit) = onClose()
}
