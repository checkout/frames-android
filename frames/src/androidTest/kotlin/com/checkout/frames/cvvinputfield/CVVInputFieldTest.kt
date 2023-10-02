package com.checkout.frames.cvvinputfield

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsFocused
import androidx.compose.ui.test.assertIsNotFocused
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import com.checkout.base.model.CardScheme
import com.checkout.frames.cvvinputfield.models.CVVComponentConfig
import com.checkout.frames.style.component.base.InputFieldStyle
import org.junit.Rule
import org.junit.Test

internal class CVVInputFieldTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testCVVInputField() {
       // When
        composeTestRule.setContent {
            CVVInputField(config = CVVComponentConfig(CardScheme.VISA, {}, InputFieldStyle()))
        }

        // Then
        composeTestRule.onNodeWithText("").assertIsDisplayed().assertIsNotFocused().performTextInput("123")
        composeTestRule.onNodeWithText("123").assertIsDisplayed().assertIsFocused()
    }
}
