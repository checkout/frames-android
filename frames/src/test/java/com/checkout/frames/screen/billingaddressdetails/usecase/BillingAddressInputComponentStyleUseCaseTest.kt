package com.checkout.frames.screen.billingaddressdetails.usecase

import android.annotation.SuppressLint
import com.checkout.base.mapper.Mapper
import com.checkout.base.usecase.UseCase
import com.checkout.frames.mapper.ContainerStyleToModifierMapper
import com.checkout.frames.mapper.InputComponentStyleToViewStyleMapper
import com.checkout.frames.mapper.InputFieldStyleToViewStyleMapper
import com.checkout.frames.mapper.TextLabelStyleToViewStyleMapper
import com.checkout.frames.mapper.billingaddressdetails.BillingAddressInputComponentStyleToViewStyleMapper
import com.checkout.frames.mock.BillingAddressDetailsTestData
import com.checkout.frames.screen.billingaddress.billingaddressdetails.models.BillingFormFields
import com.checkout.frames.screen.billingaddress.usecase.BillingAddressInputComponentStyleUseCase
import com.checkout.frames.style.component.billingformdetails.BillingAddressInputComponentStyle
import com.checkout.frames.style.component.billingformdetails.HeaderComponentStyle
import com.checkout.frames.style.screen.BillingAddressDetailsStyle
import com.checkout.frames.style.view.BillingAddressInputComponentViewStyle
import com.checkout.frames.style.view.InputComponentViewStyle
import com.checkout.frames.style.view.InputFieldViewStyle
import com.checkout.frames.style.view.TextLabelViewStyle
import com.checkout.frames.style.view.billingformdetails.BillingAddressInputComponentsViewContainerStyle
import io.mockk.every
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.amshove.kluent.internal.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@SuppressLint("NewApi")
@ExtendWith(MockKExtension::class)
internal class BillingAddressInputComponentStyleUseCaseTest {

    @SpyK
    lateinit var mockBillingAddressInputComponentStyleMapper: Mapper<BillingAddressInputComponentStyle,
            BillingAddressInputComponentViewStyle>

    private lateinit var billingAddressInputComponentStyleUseCase: UseCase<BillingAddressDetailsStyle,
            BillingAddressInputComponentsViewContainerStyle>

    init {
        initMappers()
    }

    @BeforeEach
    fun setUp() {
        every { mockBillingAddressInputComponentStyleMapper.map(any()) } returns BillingAddressInputComponentViewStyle(
            addressFieldName = BillingFormFields.AddressLineOne.name,
            inputComponentViewStyle = InputComponentViewStyle(
                isInputFieldOptional = false,
                errorMessageStyle = TextLabelViewStyle(),
                infoStyle = TextLabelViewStyle(),
                inputFieldStyle = InputFieldViewStyle(),
                subtitleStyle = TextLabelViewStyle(),
                titleStyle = TextLabelViewStyle()
            )
        )

        billingAddressInputComponentStyleUseCase = BillingAddressInputComponentStyleUseCase(
            mockBillingAddressInputComponentStyleMapper
        )
    }

    @Test
    fun `when custom billing form fields styles invoked then valid input component view style is received`() {
        // Given
        val expectedInputComponentStateList = BillingAddressDetailsTestData.fetchInputComponentStateList()

        BillingAddressDetailsTestData.fetchInputComponentStyleValues().forEach {
            every {
                mockBillingAddressInputComponentStyleMapper.map(
                    BillingAddressInputComponentStyle(
                        it.key.name,
                        it.value
                    )
                )
            } returns BillingAddressInputComponentViewStyle(
                addressFieldName = it.key.name,
                inputComponentViewStyle = InputComponentViewStyle(
                    isInputFieldOptional = false,
                    errorMessageStyle = TextLabelViewStyle(),
                    infoStyle = TextLabelViewStyle(),
                    inputFieldStyle = InputFieldViewStyle(),
                    subtitleStyle = TextLabelViewStyle(),
                    titleStyle = TextLabelViewStyle()
                )

            )
        }

        // When
        val style = BillingAddressDetailsStyle(
            HeaderComponentStyle(),
            BillingAddressDetailsTestData.inputComponentsContainerStyle()
        )

        val result = billingAddressInputComponentStyleUseCase.execute(style)

        expectedInputComponentStateList.forEachIndexed { index, state ->
            with(result.inputComponentViewStyleList[index]) {
                assertEquals(state.addressFieldName, addressFieldName)
                assertEquals(state.isInputFieldOptional, inputComponentViewStyle.isInputFieldOptional)
            }
        }
    }

    private fun initMappers() {
        val textLabelStyleMapper = TextLabelStyleToViewStyleMapper(ContainerStyleToModifierMapper())
        mockBillingAddressInputComponentStyleMapper = BillingAddressInputComponentStyleToViewStyleMapper(
            InputComponentStyleToViewStyleMapper(
                textLabelStyleMapper,
                InputFieldStyleToViewStyleMapper(textLabelStyleMapper),
                ContainerStyleToModifierMapper()
            )
        )
    }
}
