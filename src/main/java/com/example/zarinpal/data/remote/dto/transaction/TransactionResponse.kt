package com.example.zarinpal.data.remote.dto.transaction

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class TransactionResponse(
    @SerialName("data") val data: Data?
)

@Keep
@Serializable
data class Data(
    @SerialName("Session") val session: List<Session>?
)

@Keep
@Serializable
data class Session(
    @SerialName("id") val id: String,
    @SerialName("status") val status: String,
    @SerialName("amount") val amount: Long,
    @SerialName("description") val description: String,
    @SerialName("created_at") val createdAt: String
)
