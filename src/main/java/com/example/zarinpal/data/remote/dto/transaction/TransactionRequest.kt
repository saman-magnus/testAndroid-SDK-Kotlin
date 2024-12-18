package com.example.zarinpal.data.remote.dto.transaction

import androidx.annotation.Keep
import com.example.zarinpal.data.remote.dto.Config
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class TransactionRequest(
    @SerialName("terminal_id")
    val terminalId: String,
    val filter: String,
    val limit: Int,
    val offset: Int,
)
