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

/**
 * ZarinPal class handles all interactions with the ZarinPal API.
 * Provides methods for creating payments, verifying payments, refunds, reversals, and more.
 */
class ZarinPal(config: Config) {

    // Instance of PaymentService used for API calls
    private val service = PaymentService.create(config)

    // Configuration object containing sandbox mode and other settings
    private val config = config

    /**
     * Creates a new payment and returns the response.
     * @param paymentRequest Request data for creating the payment.
     * @param redirectUrl Callback function to handle the payment gateway URL and status.
     * @return [CreatePaymentDataResponse] containing details of the created payment.
     */
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

    /**
     * Verifies a payment using the authority code.
     * @param paymentVerifyRequest Request data for verifying the payment.
     * @return [PaymentVerificationDataResponse] containing verification details.
     */
    fun paymentVerify(
        paymentVerifyRequest: PaymentVerifyRequest
    ): PaymentVerificationDataResponse? {
        return runBlocking {
            service.paymentVerify(paymentVerifyRequest)
        }
    }

    /**
     * Inquires about a payment status.
     * @param paymentInquiryRequest Request data for the payment inquiry.
     * @return [PaymentInquiryDataResponse] containing inquiry details.
     */
    fun paymentInquiry(
        paymentInquiryRequest: PaymentInquiryRequest
    ): PaymentInquiryDataResponse? {
        return runBlocking {
            service.paymentInquiry(paymentInquiryRequest)
        }
    }

    /**
     * Retrieves unverified payments.
     * @param paymentUnVerifiedRequest Optional request data for retrieving unverified payments.
     * @return [PaymentUnVerifiedDataResponse] containing unverified payment details.
     */
    fun paymentUnVerified(
        paymentUnVerifiedRequest: PaymentUnVerifiedRequest = PaymentUnVerifiedRequest()
    ): PaymentUnVerifiedDataResponse? {
        return runBlocking {
            service.paymentUnVerified(paymentUnVerifiedRequest)
        }
    }

    /**
     * Retrieves unverified payments.
     * @param paymentUnVerifiedRequest Optional request data for retrieving unverified payments.
     * @return [PaymentUnVerifiedDataResponse] containing unverified payment details.
     */
    fun paymentReverse(
        paymentReverseRequest: PaymentReverseRequest
    ): PaymentReverseDataResponse? {
        return runBlocking {
            service.paymentReverse(paymentReverseRequest)
        }
    }

    /**
     * Retrieves transaction history.
     * @param transactionRequest Request data for fetching transactions.
     * @return A list of [Session] objects containing transaction details.
     */
    fun getTransactions(
        transactionRequest: TransactionRequest
    ): List<Session>? {
        return runBlocking {
            service.getTransactions(transactionRequest)
        }
    }

    /**
     * Processes a payment refund.
     * @param paymentRefundRequest Request data for refunding the payment.
     * @return [PaymentRefundResponse] containing refund details.
     */
    fun paymentRefund(
        paymentRefundRequest: PaymentRefundRequest
    ): PaymentRefundResponse? {
        return runBlocking {
            service.paymentRefund(paymentRefundRequest)
        }
    }
}