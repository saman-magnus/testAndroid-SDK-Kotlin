package com.example.zarinpal

import com.example.zarinpal.data.remote.HttpRoutes
import com.example.zarinpal.data.remote.PaymentService
import com.example.zarinpal.data.remote.dto.Config
import com.example.zarinpal.data.remote.dto.create.CreatePaymentDataResponse
import com.example.zarinpal.data.remote.dto.create.CreatePaymentRequest
import com.example.zarinpal.data.remote.dto.inquiry.PaymentInquiryDataResponse
import com.example.zarinpal.data.remote.dto.inquiry.PaymentInquiryRequest
import com.example.zarinpal.data.remote.dto.refund.PaymentRefundRequest
import com.example.zarinpal.data.remote.dto.refund.PaymentRefundResponse
import com.example.zarinpal.data.remote.dto.reverse.PaymentReverseDataResponse
import com.example.zarinpal.data.remote.dto.reverse.PaymentReverseRequest
import com.example.zarinpal.data.remote.dto.transaction.Session
import com.example.zarinpal.data.remote.dto.transaction.TransactionRequest
import com.example.zarinpal.data.remote.dto.unVerified.PaymentUnVerifiedDataResponse
import com.example.zarinpal.data.remote.dto.unVerified.PaymentUnVerifiedRequest
import com.example.zarinpal.data.remote.dto.verification.PaymentVerificationDataResponse
import com.example.zarinpal.data.remote.dto.verification.PaymentVerifyRequest
import kotlinx.coroutines.runBlocking

class ZarinPal(config: Config) {

    private val service = PaymentService.create(config)
    private val config = config

    fun createPayment(
        paymentRequest: CreatePaymentRequest,
        redirectUrl: (paymentGatewayUri: String, status: Int) -> Unit
    ): CreatePaymentDataResponse? {
        return runBlocking {
            val response = service.createPayment(paymentRequest)
            val paymentGatewayUri = HttpRoutes.getRedirectUrl(
                sandBox = paymentRequest.sandBox ?: config.sandBox,
                authority = response?.authority ?: ""
            )
            redirectUrl(paymentGatewayUri, response?.code ?: 0)
            response
        }
    }

    fun paymentVerify(
        paymentVerifyRequest: PaymentVerifyRequest
    ): PaymentVerificationDataResponse? {
        return runBlocking {
            service.paymentVerify(paymentVerifyRequest)
        }
    }

    fun paymentInquiry(
        paymentInquiryRequest: PaymentInquiryRequest
    ): PaymentInquiryDataResponse? {
        return runBlocking {
            service.paymentInquiry(paymentInquiryRequest)
        }
    }

    fun paymentUnVerified(
        paymentUnVerifiedRequest: PaymentUnVerifiedRequest = PaymentUnVerifiedRequest()
    ): PaymentUnVerifiedDataResponse? {
        return runBlocking {
            service.paymentUnVerified(paymentUnVerifiedRequest)
        }
    }

    fun paymentReverse(
        paymentReverseRequest: PaymentReverseRequest
    ): PaymentReverseDataResponse? {
        return runBlocking {
            service.paymentReverse(paymentReverseRequest)
        }
    }

    fun getTransactions(
        transactionRequest: TransactionRequest
    ): List<Session>? {
        return runBlocking {
            service.getTransactions(transactionRequest)
        }
    }

    fun paymentRefund(
        paymentRefundRequest: PaymentRefundRequest
    ): PaymentRefundResponse? {
        return runBlocking {
            service.paymentRefund(paymentRefundRequest)
        }
    }
}