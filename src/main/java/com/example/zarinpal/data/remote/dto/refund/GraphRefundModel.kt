package com.example.zarinpal.data.remote.dto.refund

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GraphRefundModel(
    val query :String,
    val variables: PaymentRefundRequest
)