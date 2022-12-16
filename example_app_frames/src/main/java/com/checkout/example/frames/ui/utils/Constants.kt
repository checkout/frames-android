package com.checkout.example.frames.ui.utils

import com.checkout.base.model.Environment

/**
 * Target platform environment
 */
val ENVIRONMENT: Environment = Environment.SANDBOX

/**
 * Replace with public key from Hub in Sandbox Environment
 */
const val PUBLIC_KEY = "pk_test_b37b8b6b-fc9a-483f-a77e-3386b606f90e"

/**
 * Replace with Secret key from Hub in Sandbox Environment
 */
const val SECRET_KEY = "sk_test_568e6077-a08f-4692-9237-cc6c48dcf6aa"

/**
 * Replace with Success/Failure Urls from Hub in Sandbox Environment
 */
const val SUCCESS_URL = "https://httpstat.us/200?q=Success"
const val FAILURE_URL = "https://httpstat.us/200?q=Failure"

const val CORNER_RADIUS_PERCENT = 12
const val URL_IDENTIFIER = "URL"
