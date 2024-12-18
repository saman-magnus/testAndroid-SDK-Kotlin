package com.example.zarinpal.data.remote.dto.reverse

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Keep
@Serializable
data class PaymentReverseResponse(
    val data: PaymentReverseDataResponse,
    val errors: JsonElement?
)

@Keep
@Serializable
data class PaymentReverseDataResponse(
    val code: Int,
    val message: String,
)
