package com.example.zarinpal.data.remote


import com.example.zarinpal.data.remote.dto.Config
import com.example.zarinpal.data.remote.dto.create.CreatePaymentRequest
import com.example.zarinpal.data.remote.dto.create.CreatePaymentResponse
import com.example.zarinpal.data.remote.dto.inquiry.PaymentInquiryRequest
import com.example.zarinpal.data.remote.dto.inquiry.PaymentInquiryResponse
import com.example.zarinpal.data.remote.dto.reverse.PaymentReverseRequest
import com.example.zarinpal.data.remote.dto.reverse.PaymentReverseResponse
import com.example.zarinpal.data.remote.dto.transaction.GraphTransactionModel
import com.example.zarinpal.data.remote.dto.transaction.TransactionRequest
import com.example.zarinpal.data.remote.dto.transaction.TransactionResponse
import com.example.zarinpal.data.remote.dto.unVerified.PaymentUnVerifiedRequest
import com.example.zarinpal.data.remote.dto.unVerified.PaymentUnVerifiedResponse
import com.example.zarinpal.data.remote.dto.verification.PaymentVerificationResponse
import com.example.zarinpal.data.remote.dto.verification.PaymentVerifyRequest
import io.ktor.client.HttpClient
import io.ktor.client.features.ClientRequestException
import io.ktor.client.features.RedirectResponseException
import io.ktor.client.features.ServerResponseException
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.url

class PaymentServiceImpl(
    private val client: HttpClient,
    private val config: Config
) : PaymentService {

    override suspend fun createPayment(paymentRequest: CreatePaymentRequest): CreatePaymentResponse? {
        return try {
            val route = HttpRoutes.createPayment(paymentRequest.sandBox ?: config.sandBox)

            client.post<CreatePaymentResponse> {
                url(route)
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

    override suspend fun paymentReverse(paymentReverseRequest: PaymentReverseRequest): PaymentReverseResponse? {
        return try {
            val route =
                HttpRoutes.paymentReverse(paymentReverseRequest.sandBox ?: config.sandBox)
            client.post<PaymentReverseResponse> {
                url(route)
                body = paymentReverseRequest.copyWithConfig(config)
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

    override suspend fun getTransactions(transactionRequest: TransactionRequest): TransactionResponse? {
        return try {
            val query = """
        query Sessions(${'$'}terminal_id: ID!, ${'$'}filter: FilterEnum, ${'$'}id: ID, ${'$'}reference_id: String, ${'$'}rrn: String, ${'$'}card_pan: String, ${'$'}email: String, ${'$'}mobile: CellNumber, ${'$'}description: String, ${'$'}limit: Int, ${'$'}offset: Int) { Session(terminal_id: ${'$'}terminal_id, filter: ${'$'}filter, id: ${'$'}id, reference_id: ${'$'}reference_id, rrn: ${'$'}rrn, card_pan: ${'$'}card_pan, email: ${'$'}email, mobile: ${'$'}mobile, description: ${'$'}description, limit: ${'$'}limit, offset: ${'$'}offset) { id, status, amount, description, created_at } }
    """.trimIndent()

            val token = transactionRequest.token ?: config.token
            client.post<TransactionResponse> {
                url(HttpRoutes.BASE_URL_GRAPH)
                header("Authorization", "Bearer $token")
                body = GraphTransactionModel(query = query, variables = transactionRequest)
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