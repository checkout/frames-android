package com.checkout.sdk.usecase

import com.checkout.sdk.store.DataStore
import com.checkout.sdk.yearinput.YearSelectedUseCase
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class YearSelectedUseCaseTest {

    @Mock
    private lateinit var dataStoreMock: DataStore

    @Test
    fun `given year is selected then it should be communicated to the data store and called back`() {
        val years = listOf("2022", "2023")
        val expectedPosition = 1

        val position = YearSelectedUseCase(
            dataStoreMock,
            years,
            expectedPosition
        ).execute()

        then(dataStoreMock).should().cardYear = years[expectedPosition]
    }
}
