package com.checkout.threedsecure.utils

import android.net.Uri

internal fun Uri?.matches(targetUri: Uri?): Boolean {
    if (this == null || targetUri == null) return false

    return checkBase(this, targetUri) && checkQuery(this, targetUri)
}

private fun checkBase(expected: Uri, target: Uri): Boolean = getBase(expected) == getBase(target)

private fun checkQuery(expected: Uri, target: Uri): Boolean {
    val expectedQuery = getQueryParameters(expected)
    val targetQuery = getQueryParameters(target)

    expectedQuery.forEach { if (!targetQuery.contains(it)) return false }

    return true
}

private fun getQueryParameters(uri: Uri) = uri.query?.split("&")?.toSet() ?: setOf()

private fun getBase(uri: Uri): String {
    val query = uri.query ?: ""

    return if (query.isBlank()) {
        uri.toString()
    } else {
        uri.toString().replace("?$query", "")
    }
}
