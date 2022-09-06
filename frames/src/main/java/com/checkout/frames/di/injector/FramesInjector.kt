package com.checkout.frames.di.injector

import android.content.Context
import com.checkout.base.model.Environment
import com.checkout.frames.component.cardnumber.CardNumberViewModel
import com.checkout.frames.component.cvv.CvvViewModel
import com.checkout.frames.di.base.InjectionClient
import com.checkout.frames.di.base.Injector
import com.checkout.frames.di.component.DaggerFramesDIComponent
import com.checkout.frames.di.component.FramesDIComponent
import com.checkout.frames.screen.paymentdetails.PaymentDetailsViewModel
import com.checkout.frames.screen.paymentform.PaymentFormViewModel
import java.lang.ref.WeakReference

internal class FramesInjector(private val component: FramesDIComponent) : Injector {

    override fun inject(client: InjectionClient) {
        when (client) {
            is CardNumberViewModel.Factory -> component.inject(client)
            is CvvViewModel.Factory -> component.inject(client)
            is PaymentDetailsViewModel.Factory -> component.inject(client)
            is PaymentFormViewModel.Factory -> component.inject(client)
            else -> throw IllegalArgumentException("Invalid injection request for ${client.javaClass.name}.")
        }
    }

    companion object {
        private var weakInjector: WeakReference<Injector>? = null

        internal fun create(
            publicKey: String,
            context: Context,
            environment: Environment
        ): Injector = weakInjector?.get() ?: run {
            val injector = FramesInjector(
                DaggerFramesDIComponent.builder()
                    .publicKey(publicKey)
                    .context(context)
                    .environment(environment)
                    .build()
            )
            weakInjector = WeakReference(injector)
            injector
        }
    }
}
