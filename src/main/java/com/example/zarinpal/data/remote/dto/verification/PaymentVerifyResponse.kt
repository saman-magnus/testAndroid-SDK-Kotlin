package com.example.zarinpal.data.remote.dto.verification

import androidx.annotation.Keep
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Keep
@Serializable
data class VerificationResponse(
    val data: VerificationDataResponse,
    val errors: JsonElement?
)

@Keep
@Serializable
data class VerificationDataResponse(
    val wages: List<String> = emptyList(),
    val code: Int,
    val message: String,
    val card_hash: String,
    val card_pan: String,
    val ref_id: Long?,
    val fee_type: String,
    val fee: Int,
    val shaparak_fee: String,
    val order_id: String? = null
)