package com.checkout.example.frames.ui.utils

import com.checkout.base.model.Environment

/**
 * Target platform environment
 */
val ENVIRONMENT: Environment = Environment.SANDBOX

/**
 * Replace with subdomain value, testing key for base url regional subdomain prefix
 */
const val REGIONAL_SUBDOMAIN = "global"

/**
 * Replace with Success/Failure Urls from Hub in Sandbox Environment
 */
const val SUCCESS_URL = "https://httpstat.us/200?q=Success"
const val FAILURE_URL = "https://httpstat.us/200?q=Failure"

const val CORNER_RADIUS_PERCENT = 12
const val URL_IDENTIFIER = "URL"
