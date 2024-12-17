@file:Suppress("PLUGIN_IS_NOT_ENABLED")

package com.example.zarinpal.data.remote.dto.verification

import com.example.zarinpal.data.remote.dto.Config
import kotlinx.serialization.Serializable

@Serializable
data class PaymentVerificationRequest(
    val merchant_id: String?=null,
    val sandBox :Boolean?=null,
    val amount: Int,
    val authority :String,
){
    fun copyWithConfig(config: Config): PaymentVerificationRequest {
        return this.copy(
            merchant_id = this.merchant_id ?: config.merchantId,
            sandBox = this.sandBox ?: config.sandBox
        )
    }
}
