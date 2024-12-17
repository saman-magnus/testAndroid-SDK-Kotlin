package com.example.zarinpal.data.remote.dto.create

import androidx.annotation.Keep
import com.example.zarinpal.data.remote.dto.Config
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
@SerialName("CreatePaymentRequest")
data class CreatePaymentRequest(
    val merchant_id: String?=null,
    val sandBox :Boolean?=null,
    val description: String,
    val callback_url: String,
    val amount: Int,
){
    fun copyWithConfig(config: Config): CreatePaymentRequest {
        return this.copy(
            merchant_id = this.merchant_id ?: config.merchantId,
            sandBox = this.sandBox ?: config.sandBox
        )
    }
}
