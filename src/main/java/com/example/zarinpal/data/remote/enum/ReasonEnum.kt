package com.example.zarinpal.data.remote.enum

import kotlinx.serialization.Serializable


@Serializable
enum class ReasonEnum {
    DUPLICATE_TRANSACTION,
    SUSPICIOUS_TRANSACTION,
    CUSTOMER_REQUEST,
    OTHER
}