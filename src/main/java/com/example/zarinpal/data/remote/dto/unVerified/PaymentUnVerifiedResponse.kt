package com.example.zarinpal.data.remote.dto.unVerified

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Keep
@Serializable
data class PaymentUnVerifiedResponse(
    val data: PaymentUnVerifiedDataResponse,
    val errors: JsonElement?
)

@Keep
@Serializable
data class PaymentUnVerifiedDataResponse(
    val code: Int,
    val message: String,
    val authorities: List<AuthorityResponse>?
)

@Keep
@Serializable
data class AuthorityResponse(
    val authority: String,
    val amount: Int,
    @SerialName("callback_url") val callbackUrl: String,
    val referer: String,
    val date: String
)
