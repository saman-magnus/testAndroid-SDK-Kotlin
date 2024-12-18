
package com.example.zarinpal.data.remote.dto.reverse

import com.example.zarinpal.data.remote.dto.Config
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaymentReverseRequest(
    @SerialName("merchant_id")
    val merchantId: String?=null,
    val sandBox :Boolean?=null,
    val authority :String,
){
    fun copyWithConfig(config: Config): PaymentReverseRequest {
        return this.copy(
            merchantId = this.merchantId ?: config.merchantId,
            sandBox = this.sandBox ?: config.sandBox
        )
    }
}
