package com.checkout.frames.usecase

import com.checkout.base.usecase.UseCase

internal class ClosePaymentFlowUseCase(
    private val onClose: () -> Unit,
) : UseCase<Unit, Unit> {

    override fun execute(data: Unit) = onClose()
}
