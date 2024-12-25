package com.example.zarinpal.data.remote.enum

import kotlinx.serialization.Serializable

@Serializable
enum class FilterEnum {
    ALL,
    PAID,
    VERIFIED,
    TRASH,
    ACTIVE,
    REFUNDED
}