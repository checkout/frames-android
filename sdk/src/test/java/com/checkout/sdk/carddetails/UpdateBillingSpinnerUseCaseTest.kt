package com.checkout.sdk.carddetails

import com.checkout.sdk.models.BillingModel
import com.checkout.sdk.models.PhoneModel
import com.checkout.sdk.store.DataStore
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UpdateBillingSpinnerUseCaseTest {

    @Mock
    private lateinit var dataStore: DataStore

    private lateinit var useCase: UpdateBillingSpinnerUseCase

    @Before
    fun setup() {
        useCase = UpdateBillingSpinnerUseCase(dataStore, SELECT_TEXT, ADD_TEXT, EDIT_TEXT, FORMAT)
    }

    @Test
    fun `given data store has a valid address when we execute then we get the formatted address and the edit string`() {
        val billingModel = BillingModel(
            "Flat 4",
            "39 Wendell St",
            "",
            "",
            "London",
            "Middlesex",
            PhoneModel("uk", "+44")
        )
        given(dataStore.customerAddress1).willReturn(billingModel.addressLine1)
        given(dataStore.customerAddress2).willReturn(billingModel.addressLine2)
        given(dataStore.customerCity).willReturn(billingModel.city)
        given(dataStore.customerState).willReturn(billingModel.state)
        val expected = listOf(
            String.format(
                FORMAT,
                billingModel.addressLine1,
                billingModel.addressLine2,
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
