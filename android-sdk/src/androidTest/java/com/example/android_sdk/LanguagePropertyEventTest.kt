package com.example.android_sdk

import android.content.Context
import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class LanguagePropertyEventTest {
    private var appContext: Context? = null

    @Before
    fun setUp() {
        appContext = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun test_language_property_of_paymentFormPresented_event_returnsCorrectValue() {
        setLocale(Locale.GERMANY)
        TestCase.assertTrue("de_DE" == Locale.getDefault().toString())
    }

    @Test
    fun test_language_property_of_paymentFormPresented_event_returnWrongValue() {
        setLocale(Locale.FRENCH)
        TestCase.assertFalse("en_US" == Locale.getDefault().language)
    }

    @After
    fun tearDown() {
        appContext = null
    }

    private fun setLocale(locale: Locale?) {
        val resources = InstrumentationRegistry.getInstrumentation().targetContext.resources
        Locale.setDefault(locale!!)
        val config = resources.configuration.also {
            it.setLocale(locale)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            appContext?.createConfigurationContext(config)
        } else {
            resources.updateConfiguration(config, resources.displayMetrics)
        }
    }
}