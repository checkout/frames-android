package com.checkout.android_sdk.Response

import com.google.gson.annotations.SerializedName

data class JWKSResponse(

	@field:SerializedName("keys")
	val keys: List<JWK?>
)

data class JWK(

	@field:SerializedName("kty")
	val kty: String,

	@field:SerializedName("e")
	val E: String,

	@field:SerializedName("use")
	val use: String,

	@field:SerializedName("kid")
	val kid: String,

	@field:SerializedName("alg")
	val alg: String,

	@field:SerializedName("n")
	val N: String
)
