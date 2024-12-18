package com.example.zarinpal.data.remote.dto.create

import androidx.annotation.Keep
import com.example.zarinpal.data.remote.dto.Config
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
@SerialName("CreatePaymentRequest")
data class CreatePaymentRequest(
    @SerialName("merchant_id")
    val merchantId: String? = null,
    val sandBox :Boolean?=null,
    val description: String,
    @SerialName("callback_url")
    val callbackUrl: String,
    val amount: Int,
){
    fun copyWithConfig(config: Config): CreatePaymentRequest {
        return this.copy(
            merchantId = this.merchantId ?: config.merchantId,
            sandBox = this.sandBox ?: config.sandBox
        )
    }
}
