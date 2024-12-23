package com.example.zarinpal.data.remote

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
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json

interface PaymentService {

    suspend fun createPayment(paymentRequest: CreatePaymentRequest): CreatePaymentDataResponse?

    suspend fun paymentVerify(paymentVerifyRequest: PaymentVerifyRequest): PaymentVerificationDataResponse?

    suspend fun paymentInquiry(paymentInquiryRequest: PaymentInquiryRequest): PaymentInquiryDataResponse?

    suspend fun paymentUnVerified(paymentUnVerifiedRequest: PaymentUnVerifiedRequest): PaymentUnVerifiedDataResponse?

    suspend fun paymentReverse(paymentReverseRequest: PaymentReverseRequest): PaymentReverseDataResponse?

    suspend fun getTransactions(transactionRequest: TransactionRequest): List<Session>?

    suspend fun paymentRefund(paymentRefundRequest: PaymentRefundRequest): PaymentRefundResponse?

    companion object {
        fun create(config: Config): PaymentService {
            return PaymentServiceImpl(
                config = config,
                client = HttpClient(Android) {
                    install(Logging) {
                        level = LogLevel.ALL
                    }
                    install(JsonFeature) {
                        serializer = KotlinxSerializer(
                            Json {
                                ignoreUnknownKeys = true
                            }
                        )
                    }
                    defaultRequest {
                        header("User-Agent", "ZarinPalSdk/v.1.0.0 (android kotlin)")
                        header("Content-Type", "application/json")
                        contentType(ContentType.Application.Json)
                    }
                },
            )
        }
    }
}