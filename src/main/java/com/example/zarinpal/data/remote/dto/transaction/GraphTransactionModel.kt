package com.example.zarinpal.data.remote.dto.transaction

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GraphTransactionModel(
    val query :String,
    val variables: TransactionRequest
)