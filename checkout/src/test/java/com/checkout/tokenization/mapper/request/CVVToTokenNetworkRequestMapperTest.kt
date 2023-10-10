package com.checkout.tokenization.mapper.request

import com.checkout.mock.TokenizationRequestTestData
import com.checkout.tokenization.entity.TokenDataEntity
import com.checkout.tokenization.request.CVVTokenNetworkRequest
import com.checkout.tokenization.utils.TokenizationConstants
import org.amshove.kluent.internal.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class CVVToTokenNetworkRequestMapperTest {
    private lateinit var cvvToTokenNetworkRequestMapper: CVVToTokenNetworkRequestMapper

    @BeforeEach
    fun setUp() {
        cvvToTokenNetworkRequestMapper = CVVToTokenNetworkRequestMapper()
    }

    @Test
    fun `mapping of CVVTokenizationRequest to CVVTokenNetworkRequest data`() {
        // Given
        val expectedCVVTokenNetworkRequest = CVVTokenNetworkRequest(
            TokenizationConstants.CVV,
            TokenDataEntity("123"),
        )

        val request = TokenizationRequestTestData.cvvTokenizationRequest

        // When
        val actualTokenRequestData = cvvToTokenNetworkRequestMapper.map(request)

        // Then
        assertEquals(expectedCVVTokenNetworkRequest, actualTokenRequestData)
    }
}
