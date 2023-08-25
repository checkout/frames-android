package com.checkout.frames.screen.billingaddressdetails.usecase

import android.annotation.SuppressLint
import com.checkout.base.mapper.Mapper
import com.checkout.base.usecase.UseCase
import com.checkout.frames.component.base.InputComponentState
import com.checkout.frames.component.billingaddressfields.BillingAddressInputComponentState
import com.checkout.frames.component.billingaddressfields.BillingAddressInputComponentsContainerState
import com.checkout.frames.mapper.ImageStyleToComposableImageMapper
import com.checkout.frames.mapper.InputComponentStyleToStateMapper
import com.checkout.frames.mapper.TextLabelStyleToStateMapper
import com.checkout.frames.mapper.InputFieldStyleToInputFieldStateMapper
import com.checkout.frames.mapper.billingaddressdetails.BillingAddressInputComponentStyleToStateMapper
import com.checkout.frames.mock.BillingAddressDetailsTestData
import com.checkout.frames.screen.billingaddress.billingaddressdetails.models.BillingFormFields
import com.checkout.frames.screen.billingaddress.usecase.BillingAddressInputComponentStateUseCase
import com.checkout.frames.style.component.billingformdetails.BillingAddressInputComponentStyle
import com.checkout.frames.style.component.billingformdetails.HeaderComponentStyle
import com.checkout.frames.style.screen.BillingAddressDetailsStyle
import io.mockk.every
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import org.amshove.kluent.internal.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@SuppressLint("NewApi")
@ExtendWith(MockKExtension::class)
internal class BillingAddressInputComponentStateUseCaseTest {

    @SpyK
    lateinit var mockBillingAddressInputComponentStateMapper: Mapper<BillingAddressInputComponentStyle,
            BillingAddressInputComponentState>

    private lateinit var billingAddressInputComponentStateUseCase: UseCase<BillingAddressDetailsStyle,
            BillingAddressInputComponentsContainerState>

    init {
        initMappers()
    }

    @BeforeEach
    fun setUp() {
        every { mockBillingAddressInputComponentStateMapper.map(any()) } returns BillingAddressInputComponentState(
            addressFieldName = BillingFormFields.CardHolderName.name,
            inputComponentState = InputComponentState(),
            isAddressFieldValid = MutableStateFlow(false)
        )

        billingAddressInputComponentStateUseCase = BillingAddressInputComponentStateUseCase(
            mockBillingAddressInputComponentStateMapper
        )
    }

    @Test
    fun `when custom billing form fields styles invoked then valid input container state is received`() {
        // Given
        val expectedInputComponentStateList = BillingAddressDetailsTestData.fetchInputComponentStateList()

        BillingAddressDetailsTestData.fetchInputComponentStyleValues().forEach {
            every {
                mockBillingAddressInputComponentStateMapper.map(
                    BillingAddressInputComponentStyle(
                        it.key.name,
                        it.value
                    )
                )
            } returns BillingAddressInputComponentState(
                addressFieldName = it.key.name,
                inputComponentState = InputComponentState(),
                isAddressFieldValid = MutableStateFlow(false)
            )
        }

        // When
        val style = BillingAddressDetailsStyle(
            HeaderComponentStyle(),
            BillingAddressDetailsTestData.inputComponentsContainerStyle()
        )

        val result = billingAddressInputComponentStateUseCase.execute(style)

        expectedInputComponentStateList.forEachIndexed { index, state ->
            with(result.inputComponentStateList[index]) {
                assertEquals(state.addressFieldName, addressFieldName)
                assertEquals(state.isInputFieldOptional, isInputFieldOptional)
                assertEquals(state.isAddressFieldValid.value, isAddressFieldValid.value)
                assertEquals(state.addressFieldText.value, addressFieldText.value)
            }
        }
    }

    private fun initMappers() {
        val imageStyleToComposableImageMapper = ImageStyleToComposableImageMapper()
        mockBillingAddressInputComponentStateMapper = BillingAddressInputComponentStyleToStateMapper(
            InputComponentStyleToStateMapper(
                TextLabelStyleToStateMapper(imageStyleToComposableImageMapper),
                InputFieldStyleToInputFieldStateMapper(imageStyleToComposableImageMapper)
            )
        )
    }
}
