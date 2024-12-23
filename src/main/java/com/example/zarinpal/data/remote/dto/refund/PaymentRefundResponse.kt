package com.example.zarinpal.data.remote.dto.refund

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaymentRefundResponseModel(
    val data: PaymentRefundDataModel
)

@Serializable
data class PaymentRefundDataModel(
    val resource: PaymentRefundResponse
)

@Serializable
data class PaymentRefundResponse(
    @SerialName("terminal_id") val terminalId: String,
    val id: String,
    val amount: Int,
    val timeline: PaymentRefundTimeline
)

@Serializable
data class PaymentRefundTimeline(
    @SerialName("refund_amount") val refundAmount: Int,
    @SerialName("refund_time") val refundTime: String,
    @SerialName("refund_status") val refundStatus: String
)