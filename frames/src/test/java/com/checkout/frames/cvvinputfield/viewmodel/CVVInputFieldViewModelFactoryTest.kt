package com.checkout.frames.cvvinputfield.viewmodel

import com.checkout.CVVComponentValidatorFactory
import com.checkout.base.model.CardScheme
import com.checkout.frames.cvvinputfield.models.CVVComponentConfig
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
internal class CVVInputFieldViewModelFactoryTest {
    private lateinit var config: CVVComponentConfig

    private lateinit var cvvComponentValidator: CVVComponentValidator

    private lateinit var factory: CVVInputFieldViewModelFactory

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
    fun testCreateCVVInputFieldViewModel() {
        // Given
        factory = CVVInputFieldViewModelFactory(
            config, cvvComponentValidator, inputFieldStateMapper, inputFieldStyleMapper
        )

        // When
        val viewModel = factory.create(CVVInputFieldViewModel::class.java)

        // Then
        assertEquals(CVVInputFieldViewModel::class.java, viewModel.javaClass)
        assertEquals(viewModel.cvvComponentConfig, config)
        assertEquals(viewModel.cvvComponentValidator, cvvComponentValidator)
        assertEquals(viewModel.inputFieldStateMapper, inputFieldStateMapper)
        assertEquals(viewModel.inputFieldStyleMapper, inputFieldStyleMapper)
    }
}
