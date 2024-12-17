package com.example.zarinpal

import com.example.zarinpal.data.remote.HttpRoutes
import com.example.zarinpal.data.remote.PaymentService
import com.example.zarinpal.data.remote.dto.Config
import com.example.zarinpal.data.remote.dto.create.CreatePaymentRequest
import com.example.zarinpal.data.remote.dto.create.CreatePaymentResponse
import com.example.zarinpal.data.remote.dto.verification.PaymentVerifyRequest
import com.example.zarinpal.data.remote.dto.verification.VerificationResponse
import kotlinx.coroutines.runBlocking

class ZarinPal(config: Config) {

    private val service = PaymentService.create(config)
    private val config = config

    fun createPayment(
        paymentRequest: CreatePaymentRequest,
        redirectUrl: (paymentGatewayUri: String,status:Int) -> Unit
    ): CreatePaymentResponse? {
        return runBlocking {
            val response = service.createPayment(paymentRequest)
            val paymentGatewayUri = HttpRoutes.getRedirectUrl(
                sandBox = paymentRequest.sandBox ?: config.sandBox,
                authority = response?.data?.authority ?: ""
            )
            redirectUrl(paymentGatewayUri, response?.data?.code ?: 0)
            response
        }
    }

    fun paymentVerify(
        paymentVerifyRequest: PaymentVerifyRequest
    ): VerificationResponse? {
        return runBlocking {
            service.paymentVerify(paymentVerifyRequest)
        }
    }
}