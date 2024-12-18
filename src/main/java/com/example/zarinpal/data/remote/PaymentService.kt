package com.example.zarinpal.data.remote

import com.example.zarinpal.data.remote.dto.Config
import com.example.zarinpal.data.remote.dto.create.CreatePaymentRequest
import com.example.zarinpal.data.remote.dto.create.CreatePaymentResponse
import com.example.zarinpal.data.remote.dto.inquiry.PaymentInquiryRequest
import com.example.zarinpal.data.remote.dto.inquiry.PaymentInquiryResponse
import com.example.zarinpal.data.remote.dto.reverse.PaymentReverseRequest
import com.example.zarinpal.data.remote.dto.reverse.PaymentReverseResponse
import com.example.zarinpal.data.remote.dto.unVerified.PaymentUnVerifiedRequest
import com.example.zarinpal.data.remote.dto.unVerified.PaymentUnVerifiedResponse
import com.example.zarinpal.data.remote.dto.verification.PaymentVerifyRequest
import com.example.zarinpal.data.remote.dto.verification.PaymentVerificationResponse
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logging

interface PaymentService {

    suspend fun createPayment(paymentRequest: CreatePaymentRequest): CreatePaymentResponse?

    suspend fun paymentVerify(paymentVerifyRequest: PaymentVerifyRequest): PaymentVerificationResponse?

    suspend fun paymentInquiry(paymentInquiryRequest: PaymentInquiryRequest): PaymentInquiryResponse?

    suspend fun paymentUnVerified(paymentUnVerifiedRequest: PaymentUnVerifiedRequest): PaymentUnVerifiedResponse?

    suspend fun paymentReverse(paymentReverseRequest: PaymentReverseRequest): PaymentReverseResponse?

    suspend fun getTransactions(paymentReverseRequest: PaymentReverseRequest): PaymentReverseResponse?

    companion object {
        fun create(config: Config): PaymentService {
            return PaymentServiceImpl(
                config = config,
                client = HttpClient(Android) {
                    install(Logging) {
                        level = LogLevel.ALL
                    }
                    install(JsonFeature) {
                        serializer = KotlinxSerializer()
                    }
                },
            )
        }
    }
}