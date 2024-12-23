package com.example.zarinpal.data.remote.dto.refund

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class PaymentRefundRequest(
    @SerialName("session_id")
    val sessionId: String,
    val description: String,
    val method: String,
    val reason: String,
    val amount: Int,
    val token: String? = null,
)
