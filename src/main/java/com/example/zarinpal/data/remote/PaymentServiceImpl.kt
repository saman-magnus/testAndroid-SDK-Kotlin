package com.example.zarinpal.data.remote


import com.example.zarinpal.data.remote.dto.Config
import com.example.zarinpal.data.remote.dto.create.CreatePaymentDataResponse
import com.example.zarinpal.data.remote.dto.create.CreatePaymentRequest
import com.example.zarinpal.data.remote.dto.create.CreatePaymentResponse
import com.example.zarinpal.data.remote.dto.inquiry.PaymentInquiryDataResponse
import com.example.zarinpal.data.remote.dto.inquiry.PaymentInquiryRequest
import com.example.zarinpal.data.remote.dto.inquiry.PaymentInquiryResponse
import com.example.zarinpal.data.remote.dto.refund.GraphRefundModel
import com.example.zarinpal.data.remote.dto.refund.PaymentRefundRequest
import com.example.zarinpal.data.remote.dto.refund.PaymentRefundResponse
import com.example.zarinpal.data.remote.dto.refund.PaymentRefundResponseModel
import com.example.zarinpal.data.remote.dto.reverse.PaymentReverseDataResponse
import com.example.zarinpal.data.remote.dto.reverse.PaymentReverseRequest
import com.example.zarinpal.data.remote.dto.reverse.PaymentReverseResponse
import com.example.zarinpal.data.remote.dto.transaction.GraphTransactionModel
import com.example.zarinpal.data.remote.dto.transaction.Session
import com.example.zarinpal.data.remote.dto.transaction.TransactionRequest
import com.example.zarinpal.data.remote.dto.transaction.TransactionResponse
import com.example.zarinpal.data.remote.dto.unVerified.PaymentUnVerifiedDataResponse
import com.example.zarinpal.data.remote.dto.unVerified.PaymentUnVerifiedRequest
import com.example.zarinpal.data.remote.dto.unVerified.PaymentUnVerifiedResponse
import com.example.zarinpal.data.remote.dto.verification.PaymentVerificationDataResponse
import com.example.zarinpal.data.remote.dto.verification.PaymentVerificationResponse
import com.example.zarinpal.data.remote.dto.verification.PaymentVerifyRequest
import io.ktor.client.HttpClient
import io.ktor.client.features.ClientRequestException
import io.ktor.client.features.RedirectResponseException
import io.ktor.client.features.ServerResponseException
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.client.statement.readText
import org.json.JSONArray
import org.json.JSONObject

class PaymentServiceImpl(
    private val client: HttpClient,
    private val config: Config
) : PaymentService {

    override suspend fun createPayment(paymentRequest: CreatePaymentRequest): CreatePaymentDataResponse? {
        return handleRequestWithErrorHandling {
            val route = HttpRoutes.createPayment(paymentRequest.sandBox ?: config.sandBox)

            val response = client.post<CreatePaymentResponse> {
                url(route)
                body = paymentRequest.copyWithConfig(config)
            }
            response.data
        }
    }

    override suspend fun paymentVerify(paymentVerifyRequest: PaymentVerifyRequest): PaymentVerificationDataResponse? {
        return handleRequestWithErrorHandling {
            val route = HttpRoutes.paymentVerify(paymentVerifyRequest.sandBox ?: config.sandBox)

            val response = client.post<PaymentVerificationResponse> {
                url(route)
                body = paymentVerifyRequest.copyWithConfig(config)
            }
            response.data
        }
    }

    override suspend fun paymentInquiry(paymentInquiryRequest: PaymentInquiryRequest): PaymentInquiryDataResponse? {
        return handleRequestWithErrorHandling {
            val route = HttpRoutes.paymentInquiry(paymentInquiryRequest.sandBox ?: config.sandBox)

            val response = client.post<PaymentInquiryResponse> {
                url(route)
                body = paymentInquiryRequest.copyWithConfig(config)
            }
            response.data
        }
    }

    override suspend fun paymentUnVerified(paymentUnVerifiedRequest: PaymentUnVerifiedRequest): PaymentUnVerifiedDataResponse? {
        return handleRequestWithErrorHandling {
            val route =
                HttpRoutes.paymentUnVerified(paymentUnVerifiedRequest.sandBox ?: config.sandBox)
            val response = client.post<PaymentUnVerifiedResponse> {
                url(route)
                body = paymentUnVerifiedRequest.copyWithConfig(config)
            }
            response.data
        }

    }

    override suspend fun paymentReverse(paymentReverseRequest: PaymentReverseRequest): PaymentReverseDataResponse? {
        return handleRequestWithErrorHandling {
            val route =
                HttpRoutes.paymentReverse(paymentReverseRequest.sandBox ?: config.sandBox)
            val response = client.post<PaymentReverseResponse> {
                url(route)
                body = paymentReverseRequest.copyWithConfig(config)
            }
            response.data
        }
    }

    override suspend fun getTransactions(transactionRequest: TransactionRequest): List<Session>? {
        return handleRequestWithErrorHandling {
            val query = """
        query Sessions(${'$'}terminal_id: ID!, ${'$'}filter: FilterEnum, ${'$'}id: ID, ${'$'}reference_id: String, ${'$'}rrn: String, ${'$'}card_pan: String, ${'$'}email: String, ${'$'}mobile: CellNumber, ${'$'}description: String, ${'$'}limit: Int, ${'$'}offset: Int) { Session(terminal_id: ${'$'}terminal_id, filter: ${'$'}filter, id: ${'$'}id, reference_id: ${'$'}reference_id, rrn: ${'$'}rrn, card_pan: ${'$'}card_pan, email: ${'$'}email, mobile: ${'$'}mobile, description: ${'$'}description, limit: ${'$'}limit, offset: ${'$'}offset) { id, status, amount, description, created_at } }
    """.trimIndent()

            val token = transactionRequest.token ?: config.token
            val response = client.post<TransactionResponse> {
                url(HttpRoutes.BASE_URL_GRAPH)
                header("Authorization", "Bearer $token")
                body = GraphTransactionModel(query = query, variables = transactionRequest)
            }
            response.data?.session
        }
    }

    override suspend fun paymentRefund(paymentRefundRequest: PaymentRefundRequest): PaymentRefundResponse? {
        return handleRequestWithErrorHandling {
            val query = """
        mutation AddRefund(${'$'}session_id: ID!,${'$'}amount: BigInteger!,${'$'}description: String,${'$'}method: InstantPayoutActionTypeEnum,${'$'}reason: RefundReasonEnum) {resource: AddRefund(session_id: ${'$'}session_id,amount: ${'$'}amount,description: ${'$'}description,method: ${'$'}method,reason: ${'$'}reason) {terminal_id,id,amount,timeline {refund_amount,refund_time,refund_status}}}
    """.trimIndent()

            val token = paymentRefundRequest.token ?: config.token
            val response = client.post<PaymentRefundResponseModel> {
                url(HttpRoutes.BASE_URL_GRAPH)
                header("Authorization", "Bearer $token")
                body = GraphRefundModel(query = query, variables = paymentRefundRequest)
            }
            response.data.resource
        }

    }

    private suspend fun <T> handleRequestWithErrorHandling(request: suspend () -> T): T {
        return try {
            request()
        } catch (e: RedirectResponseException) {
            // 3xx - responses
            val errorResponse = e.response.readText()
            throw Exception(processErrorResponse(errorResponse) ?: e.response.status.description)
        } catch (e: ClientRequestException) {
            // 4xx - responses
            val errorResponse = e.response.readText()
            throw Exception(processErrorResponse(errorResponse) ?: e.response.status.description)
        } catch (e: ServerResponseException) {
            // 5xx - responses
            val errorResponse = e.response.readText()
            throw Exception(processErrorResponse(errorResponse) ?: e.response.status.description)
        } catch (e: Exception) {
            throw e
        }
    }

    private fun processErrorResponse(jsonString: String?): String? {

        if (jsonString == null) return null

        try {
            val jsonObject = JSONObject(jsonString)

            // If 'errors' is an object
            if (jsonObject.has("errors") && !jsonObject.isNull("errors") && jsonObject.get("errors") is JSONObject) {
                val errorObject = jsonObject.getJSONObject("errors")
                val message :String?= errorObject.optString("message",null)
                val faMessage :String?= errorObject.optString("fa_message",null)

                if(!(faMessage ?: message).isNullOrBlank() && !(faMessage ?: message).isNullOrEmpty()) return faMessage ?: message

                val messageJsonObject = jsonObject.getString("message")
                val faMessageJsonObject :String?= jsonObject.optString("fa_message",null)

                return faMessageJsonObject ?: messageJsonObject
            }

            // If 'errors' is an array
            else if (jsonObject.has("errors") && jsonObject.get("errors") is JSONArray) {
                val errorsArray = jsonObject.getJSONArray("errors")

                // Iterate through each error in the array
                for (i in 0 until errorsArray.length()) {
                    val errorObject = errorsArray.getJSONObject(i)

                    // Check if there's a readable_code error
                    if (errorObject.has("readable_code")) {
                        val message = errorObject.getString("message")
                        val faMessage :String?= errorObject.optString("fa_message", null)

                        return faMessage ?: message
                    }
                    // Check if there's a validation error
                    else if (errorObject.has("validation")) {
                        val validationArray = errorObject.getJSONArray("validation")
                        for (j in 0 until validationArray.length()) {
                            val validationObject = validationArray.getJSONObject(j)
                            val message = validationObject.getString("message")

                            // Use the validation message
                            val faMessage :String?= validationObject.optString("fa_message", null)
                            return faMessage ?: message
                        }
                    }
                    // Check if there's a other error
                    else {
                        val message = errorObject.getString("message")
                        val faMessage :String?= errorObject.optString("fa_message", null)

                        return faMessage ?: message
                    }
                }
            }
        } catch (ex: Exception) {
            return null
        }
        return null
    }
}