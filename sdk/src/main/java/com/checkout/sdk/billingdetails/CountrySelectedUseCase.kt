package com.checkout.sdk.billingdetails

import com.checkout.sdk.architecture.UseCase
import com.checkout.sdk.billingdetails.BillingDetailsUiState.Companion.mapCountryToCode
import com.checkout.sdk.store.InMemoryStore
import com.checkout.sdk.utils.PhoneUtils


class CountrySelectedUseCase(
    private val inMemoryStore: InMemoryStore,
    val position: Int,
    private val countries: List<String>
) : UseCase<Unit> {

    private constructor(builder: Builder) : this(
        builder.store,
        builder.position,
        builder.countries
    )

    override fun execute() {
        val country = countries[position]
        if (!country.isEmpty()) {
            inMemoryStore.billingDetails = inMemoryStore.billingDetails.copy(country = country)
        }
        val countryCode = mapCountryToCode[country]
        val prefix = PhoneUtils.getPrefix(countryCode)
        if (!prefix.isEmpty()) {
            val phoneModel = inMemoryStore.billingDetails.phone.copy(countryCode = prefix)
            inMemoryStore.updatePhoneModel(phoneModel)
        }
    }

    open class Builder(val store: InMemoryStore, open val position: Int) {
        lateinit var countries: List<String>
            private set

        open fun countries(countries: List<String>) = apply { this.countries = countries }

        open fun build() = CountrySelectedUseCase(this)
    }

}
