package com.example.zarinpal.data.remote.dto.transaction

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Keep
@Serializable
data class TransactionRequest(
    @SerialName("terminal_id")
    val terminalId: String,
    val filter: String,
    val limit: Int,
    val offset: Int,
    val token: String? = null,
)
