package com.checkout.frames.di.injector

import com.checkout.frames.component.cardnumber.CardNumberViewModel
import com.checkout.frames.component.expirydate.ExpiryDateViewModel
import com.checkout.frames.di.base.InjectionClient
import com.checkout.frames.di.base.Injector
import com.checkout.frames.di.component.DaggerFramesDIComponent
import com.checkout.frames.di.component.FramesDIComponent
import java.lang.ref.WeakReference

internal class FramesInjector(private val component: FramesDIComponent) : Injector {

    override fun inject(client: InjectionClient) {
        when (client) {
            is CardNumberViewModel.Factory -> component.inject(client)
            is ExpiryDateViewModel.Factory -> component.inject(client)
            else -> throw IllegalArgumentException("Invalid injection request for ${client.javaClass.name}.")
        }
    }

    companion object {
        private var weakInjector: WeakReference<Injector>? = null

        internal fun create(): Injector = weakInjector?.get() ?: run {
            val injector = FramesInjector(DaggerFramesDIComponent.create())
            weakInjector = WeakReference(injector)
            injector
        }
    }
}
