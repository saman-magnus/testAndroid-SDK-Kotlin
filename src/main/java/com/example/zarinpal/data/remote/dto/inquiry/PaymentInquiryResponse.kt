package com.example.zarinpal.data.remote.dto.inquiry

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Keep
@Serializable
data class PaymentInquiryResponse(
    val data: PaymentInquiryDataResponse,
    val errors: JsonElement?
)

@Keep
@Serializable
data class PaymentInquiryDataResponse(
    val status:String,
    val code: Int,
    val message: String,
)
