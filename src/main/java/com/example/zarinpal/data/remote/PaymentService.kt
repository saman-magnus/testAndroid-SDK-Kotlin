package com.example.zarinpal.data.remote

import com.example.zarinpal.data.remote.dto.Config
import com.example.zarinpal.data.remote.dto.create.CreatePaymentRequest
import com.example.zarinpal.data.remote.dto.create.CreatePaymentResponse
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logging

interface PaymentService {

    suspend fun createPayment(paymentRequest: CreatePaymentRequest): CreatePaymentResponse?

//    suspend fun createPost(postRequest: PostRequest): PostResponse?

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