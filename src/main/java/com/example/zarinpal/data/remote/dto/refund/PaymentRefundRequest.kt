package com.example.zarinpal.data.remote.dto.refund

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class PaymentRefundRequest(
    @SerialName("terminal_id")
    val terminalId: String,
    val filter: String,
    val limit: Int,
    val offset: Int,
    val token: String? = null,
)
