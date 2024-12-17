@file:Suppress("PLUGIN_IS_NOT_ENABLED")

package com.example.zarinpal.data.remote.dto.create

import androidx.annotation.Keep
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Keep
@Serializable
data class CreatePaymentResponse(
    val data: CreatePaymentDataResponse,
    val errors: JsonElement?
    )

@Keep
@Serializable
data class CreatePaymentDataResponse(
    val authority: String,
    val message: String,
    val fee_type: String,
    val fee: Int,
    val code: Int,
)

