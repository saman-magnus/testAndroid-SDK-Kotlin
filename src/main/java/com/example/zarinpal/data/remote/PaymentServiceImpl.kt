package com.example.zarinpal.data.remote


import com.example.zarinpal.data.remote.dto.Config
import com.example.zarinpal.data.remote.dto.create.CreatePaymentRequest
import com.example.zarinpal.data.remote.dto.create.CreatePaymentResponse
import com.example.zarinpal.data.remote.dto.inquiry.PaymentInquiryRequest
import com.example.zarinpal.data.remote.dto.inquiry.PaymentInquiryResponse
import com.example.zarinpal.data.remote.dto.unVerified.PaymentUnVerifiedRequest
import com.example.zarinpal.data.remote.dto.unVerified.PaymentUnVerifiedResponse
import com.example.zarinpal.data.remote.dto.verification.PaymentVerificationResponse
import com.example.zarinpal.data.remote.dto.verification.PaymentVerifyRequest
import io.ktor.client.HttpClient
import io.ktor.client.features.ClientRequestException
import io.ktor.client.features.RedirectResponseException
import io.ktor.client.features.ServerResponseException
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType

class PaymentServiceImpl(
    private val client: HttpClient,
    private val config: Config
) : PaymentService {

    override suspend fun createPayment(paymentRequest: CreatePaymentRequest): CreatePaymentResponse? {
        return try {
            val route = HttpRoutes.createPayment(paymentRequest.sandBox ?: config.sandBox)

            client.post<CreatePaymentResponse> {
                url(route)
                contentType(ContentType.Application.Json)
                body = paymentRequest.copyWithConfig(config)
            }
        } catch (e: RedirectResponseException) {
            // 3xx - responses
            println("Error: ${e.response.status.description}")
            null
        } catch (e: ClientRequestException) {
            // 4xx - responses
            println("Error: ${e.response.status.description}")
            null
        } catch (e: ServerResponseException) {
            // 5xx - responses
            println("Error: ${e.response.status.description}")
            null
        } catch (e: Exception) {
            println("Error: ${e.message}")
            null
        }
    }

    override suspend fun paymentVerify(paymentVerifyRequest: PaymentVerifyRequest): PaymentVerificationResponse? {
        return try {
            val route = HttpRoutes.paymentVerify(paymentVerifyRequest.sandBox ?: config.sandBox)

            client.post<PaymentVerificationResponse> {
                url(route)
                contentType(ContentType.Application.Json)
                body = paymentVerifyRequest.copyWithConfig(config)
            }
        } catch (e: RedirectResponseException) {
            // 3xx - responses
            println("Error: ${e.response.status.description}")
            null
        } catch (e: ClientRequestException) {
            // 4xx - responses
            println("Error: ${e.response.status.description}")
            null
        } catch (e: ServerResponseException) {
            // 5xx - responses
            println("Error: ${e.response.status.description}")
            null
        } catch (e: Exception) {
            println("Error: ${e.message}")
            null
        }
    }

    override suspend fun paymentInquiry(paymentInquiryRequest: PaymentInquiryRequest): PaymentInquiryResponse? {
        return try {
            val route = HttpRoutes.paymentInquiry(paymentInquiryRequest.sandBox ?: config.sandBox)

            client.post<PaymentInquiryResponse> {
                url(route)
                contentType(ContentType.Application.Json)
                body = paymentInquiryRequest.copyWithConfig(config)
            }
        } catch (e: RedirectResponseException) {
            // 3xx - responses
            println("Error: ${e.response.status.description}")
            null
        } catch (e: ClientRequestException) {
            // 4xx - responses
            println("Error: ${e.response.status.description}")
            null
        } catch (e: ServerResponseException) {
            // 5xx - responses
            println("Error: ${e.response.status.description}")
            null
        } catch (e: Exception) {
            println("Error: ${e.message}")
            null
        }
    }

    override suspend fun paymentUnVerified(paymentUnVerifiedRequest: PaymentUnVerifiedRequest): PaymentUnVerifiedResponse? {
        return try {
            val route =
                HttpRoutes.paymentUnVerified(paymentUnVerifiedRequest.sandBox ?: config.sandBox)
            client.post<PaymentUnVerifiedResponse> {
                url(route)
                contentType(ContentType.Application.Json)
                body = paymentUnVerifiedRequest.copyWithConfig(config)
            }
        } catch (e: RedirectResponseException) {
            // 3xx - responses
            println("Error: ${e.response.status.description}")
            null
        } catch (e: ClientRequestException) {
            // 4xx - responses
            println("Error: ${e.response.status.description}")
            null
        } catch (e: ServerResponseException) {
            // 5xx - responses
            println("Error: ${e.response.status.description}")
            null
        } catch (e: Exception) {
            println("Error: ${e.message}")
            null
        }

    }
}