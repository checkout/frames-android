package com.checkout.frames.cvvcomponent.viewmodel

import com.checkout.CVVComponentValidatorFactory
import com.checkout.base.model.CardScheme
import com.checkout.frames.cvvcomponent.models.CVVComponentConfig
import com.checkout.frames.mapper.ContainerStyleToModifierMapper
import com.checkout.frames.mapper.ImageStyleToComposableImageMapper
import com.checkout.frames.mapper.InputFieldStyleToInputFieldStateMapper
import com.checkout.frames.mapper.InputFieldStyleToViewStyleMapper
import com.checkout.frames.mapper.TextLabelStyleToViewStyleMapper
import com.checkout.frames.style.component.base.InputFieldStyle
import com.checkout.validation.api.CVVComponentValidator
import io.mockk.junit5.MockKExtension
import org.amshove.kluent.internal.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class CVVComponentViewModelFactoryTest {
    private lateinit var config: CVVComponentConfig

    private lateinit var cvvComponentValidator: CVVComponentValidator

    private lateinit var factory: CVVComponentViewModelFactory

    private lateinit var inputFieldStyleMapper: InputFieldStyleToViewStyleMapper

    private lateinit var inputFieldStateMapper: InputFieldStyleToInputFieldStateMapper

    @BeforeEach
    fun setUp() {
        config = CVVComponentConfig(CardScheme.VISA, {}, InputFieldStyle())
        cvvComponentValidator = CVVComponentValidatorFactory.create()
        inputFieldStyleMapper = InputFieldStyleToViewStyleMapper(
            TextLabelStyleToViewStyleMapper(
                ContainerStyleToModifierMapper()
            )
        )
        inputFieldStateMapper = InputFieldStyleToInputFieldStateMapper(ImageStyleToComposableImageMapper())
    }

    @Test
    fun testCreateCVVComponentViewModel() {
        // Given
        factory = CVVComponentViewModelFactory(
            config, cvvComponentValidator, inputFieldStateMapper, inputFieldStyleMapper
        )

        // When
        val viewModel = factory.create(CVVComponentViewModel::class.java)

        // Then
        assertEquals(CVVComponentViewModel::class.java, viewModel.javaClass)
        assertEquals(viewModel.cvvComponentConfig, config)
        assertEquals(viewModel.cvvComponentValidator, cvvComponentValidator)
        assertEquals(viewModel.inputFieldStateMapper, inputFieldStateMapper)
        assertEquals(viewModel.inputFieldStyleMapper, inputFieldStyleMapper)
    }
}
