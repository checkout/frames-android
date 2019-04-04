package com.checkout.sdk.carddetails

import com.checkout.sdk.billingdetails.model.BillingDetail
import com.checkout.sdk.billingdetails.model.CityDetail
import com.checkout.sdk.billingdetails.model.BillingDetails
import com.checkout.sdk.models.PhoneModel
import com.checkout.sdk.store.DataStore
import com.checkout.sdk.store.InMemoryStore
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UpdateBillingSpinnerUseCaseTest {

    @Mock
    private lateinit var dataStore: DataStore

    @Mock
    private lateinit var inMemoryStore: InMemoryStore

    private lateinit var useCase: UpdateBillingSpinnerUseCase

    @Before
    fun setup() {
        useCase = UpdateBillingSpinnerUseCase(dataStore, SELECT_TEXT, ADD_TEXT, EDIT_TEXT, FORMAT)
    }

    @Ignore("Need to refactor Billing Spinner to use InMemoryStore")
    @Test
    fun `given data store has a valid address when we execute then we get the formatted address and the edit string`() {
        val billingModel = BillingDetails(
            BillingDetail("Flat 4"),
            BillingDetail("39 Wendell St"),
            CityDetail("London"),
            "",
            "",
            "Middlesex",
            PhoneModel("uk", "+44")
        )
        given(inMemoryStore.billingDetails).willReturn(billingModel)

        given(dataStore.customerState).willReturn(billingModel.state)
        val expected = listOf(
            String.format(
                FORMAT,
                billingModel.addressOne,
                billingModel.addressTwo,
                billingModel.city,
                billingModel.state
            ),
            EDIT_TEXT
        )

        val result = useCase.execute()

        assertEquals(expected, result)
    }

    @Test
    fun `given no default address when we execute we should get a list containing select and add`() {
        val expected = listOf(SELECT_TEXT, ADD_TEXT)

        val result = useCase.execute()

        assertEquals(expected, result)
    }

    companion object {
        private const val SELECT_TEXT: String = "select it"
        private const val ADD_TEXT: String = "add it"
        private const val EDIT_TEXT: String = "edit it"
        private const val FORMAT: String = "%s | %s | %s | %s"
    }
}
