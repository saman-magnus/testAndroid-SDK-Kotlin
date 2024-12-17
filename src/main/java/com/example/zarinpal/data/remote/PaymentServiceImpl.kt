package com.example.zarinpal.data.remote


import com.example.zarinpal.data.remote.dto.Config
import com.example.zarinpal.data.remote.dto.create.CreatePaymentRequest
import com.example.zarinpal.data.remote.dto.create.CreatePaymentResponse
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

            val text = client.post<CreatePaymentResponse> {
                url(route)
                contentType(ContentType.Application.Json)
                body = paymentRequest.copyWithConfig(config)
            }
            text
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