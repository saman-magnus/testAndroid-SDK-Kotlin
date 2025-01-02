package com.example.zarinpal.data.remote.dto.create

import androidx.annotation.Keep
import com.example.zarinpal.data.remote.dto.Config
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents the request data required to create a payment.
 *
 * @property merchantId The unique identifier for the merchant (nullable).
 * @property sandBox Indicates if the payment should be processed in sandbox mode (test mode).
 * @property description Description of the payment (e.g., "Order #1234").
 * @property callbackUrl The URL to redirect the user to after payment.
 * @property amount The total amount of the payment in the smallest currency unit (e.g., IRR).
 */
@Keep
@Serializable
data class CreatePaymentRequest(
    @SerialName("merchant_id")
    val merchantId: String? = null,
    val sandBox: Boolean? = null,
    val description: String,
    @SerialName("callback_url")
    val callbackUrl: String,
    val amount: Int,
    val mobile: String?=null,
    val email: String?=null,
    @SerialName("referrer_id")
    val referrerId: String?=null,
    val currency: String?=null,
    val cardPan: String?=null,
    val wages: List<WagesPaymentRequest>?=null,
) {
    /**
     * Creates a copy of the request with the merchantId and sandBox values
     * replaced by the ones from the provided [Config] if they are null.
     */
    fun copyWithConfig(config: Config): CreatePaymentRequest {
        return this.copy(
            merchantId = this.merchantId ?: config.merchantId,
            sandBox = this.sandBox ?: config.sandBox
        )
    }
}


@Keep
@Serializable
data class WagesPaymentRequest(
    val iban: String,
    val amount: Int,
    val description: String,
)
