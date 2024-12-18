package com.example.zarinpal.data.remote.dto.verification

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Keep
@Serializable
data class PaymentVerificationResponse(
    val data: PaymentVerificationDataResponse,
    val errors: JsonElement?
)

@Keep
@Serializable
data class PaymentVerificationDataResponse(
    val wages: List<String>? = emptyList(),
    val code: Int,
    val message: String,
    @SerialName("card_hash")
    val cardHash: String,
    @SerialName("card_pan")
    val cardPan: String,
    @SerialName("ref_id")
    val refId: Long?,
    @SerialName("fee_type")
    val feeType: String,
    val fee: Int,
    @SerialName("shaparak_fee")
    val shaparakFee: Int,
    @SerialName("order_id")
    val orderId: String? = null
)
