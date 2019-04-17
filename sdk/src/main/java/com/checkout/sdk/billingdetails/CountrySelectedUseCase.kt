package com.checkout.sdk.billingdetails

import com.checkout.sdk.architecture.UseCase
import com.checkout.sdk.store.InMemoryStore
import com.checkout.sdk.utils.PhoneUtils


open class CountrySelectedUseCase(
    private val countriesManager: CountriesManager,
    private val inMemoryStore: InMemoryStore,
    open val position: Int,
    private val countries: List<String>
) : UseCase<Unit> {

    private constructor(builder: Builder) : this(
        builder.countriesManager,
        builder.store,
        builder.position,
        builder.countries
    )

    override fun execute() {
        val country = countries[position]
        if (!country.isEmpty()) {
            inMemoryStore.billingDetails = inMemoryStore.billingDetails.copy(country = country)
        }
        val countryCode = countriesManager.getCountryCode(country)
        val prefix = PhoneUtils.getPrefix(countryCode)
        if (!prefix.isEmpty()) {
            val phoneModel = inMemoryStore.billingDetails.phone.copy(countryCode = prefix)
            inMemoryStore.updatePhoneModel(phoneModel)
        }
    }

    open class Builder(val countriesManager: CountriesManager, val store: InMemoryStore, open val position: Int) {
        lateinit var countries: List<String>
            private set

        open fun countries(countries: List<String>) = apply { this.countries = countries }

        open fun build() = CountrySelectedUseCase(this)
    }

}
