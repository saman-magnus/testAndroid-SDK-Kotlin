package com.example.zarinpal

import com.example.zarinpal.data.remote.HttpRoutes
import com.example.zarinpal.data.remote.PaymentService
import com.example.zarinpal.data.remote.dto.Config
import com.example.zarinpal.data.remote.dto.create.CreatePaymentRequest
import com.example.zarinpal.data.remote.dto.create.CreatePaymentResponse
import com.example.zarinpal.data.remote.dto.inquiry.PaymentInquiryRequest
import com.example.zarinpal.data.remote.dto.inquiry.PaymentInquiryResponse
import com.example.zarinpal.data.remote.dto.reverse.PaymentReverseRequest
import com.example.zarinpal.data.remote.dto.reverse.PaymentReverseResponse
import com.example.zarinpal.data.remote.dto.transaction.TransactionRequest
import com.example.zarinpal.data.remote.dto.transaction.TransactionResponse
import com.example.zarinpal.data.remote.dto.unVerified.PaymentUnVerifiedRequest
import com.example.zarinpal.data.remote.dto.unVerified.PaymentUnVerifiedResponse
import com.example.zarinpal.data.remote.dto.verification.PaymentVerificationResponse
import com.example.zarinpal.data.remote.dto.verification.PaymentVerifyRequest
import kotlinx.coroutines.runBlocking

class ZarinPal(config: Config) {

    private val service = PaymentService.create(config)
    private val config = config

    fun createPayment(
        paymentRequest: CreatePaymentRequest,
        redirectUrl: (paymentGatewayUri: String, status: Int) -> Unit
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
    ): PaymentVerificationResponse? {
        return runBlocking {
            service.paymentVerify(paymentVerifyRequest)
        }
    }

    fun paymentInquiry(
        paymentInquiryRequest: PaymentInquiryRequest
    ): PaymentInquiryResponse? {
        return runBlocking {
            service.paymentInquiry(paymentInquiryRequest)
        }
    }

    fun paymentUnVerified(
        paymentUnVerifiedRequest: PaymentUnVerifiedRequest = PaymentUnVerifiedRequest()
    ): PaymentUnVerifiedResponse? {
        return runBlocking {
            service.paymentUnVerified(paymentUnVerifiedRequest)
        }
    }

    fun paymentReverse(
        paymentReverseRequest: PaymentReverseRequest
    ): PaymentReverseResponse? {
        return runBlocking {
            service.paymentReverse(paymentReverseRequest)
        }
    }

    fun getTransactions(
        transactionRequest: TransactionRequest
    ): TransactionResponse? {
        return runBlocking {
            service.getTransactions(transactionRequest)
        }
    }
}