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
import com.example.zarinpal.utils.Validator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

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
    suspend fun createPayment(
        paymentRequest: CreatePaymentRequest,
        redirectUrl: (paymentGatewayUri: String, status: Int) -> Unit
    ): CreatePaymentDataResponse? {
        Validator.validateMerchantId(paymentRequest.merchantId ?: config.merchantId)
        Validator.validateCallbackUrl(paymentRequest.callbackUrl)
        Validator.validateAmount(paymentRequest.amount)
        Validator.validateMobile(paymentRequest.mobile)
        Validator.validateEmail(paymentRequest.email)
        Validator.validateCardPan(paymentRequest.cardPan)

        val response = withContext(Dispatchers.IO) {
            val paymentResponse = async {
                service.createPayment(paymentRequest)
            }

            val paymentGatewayUri = HttpRoutes.getRedirectUrl(
                sandBox = paymentRequest.sandBox ?: config.sandBox,
                authority = paymentResponse.await()?.authority ?: ""
            )

            withContext(Dispatchers.Main) {
                redirectUrl(paymentGatewayUri, paymentResponse.await()?.code ?: 0)
            }

            paymentResponse.await()
        }

        return response
    }

    /**
     * Verifies a payment using the authority code.
     * @param paymentVerifyRequest Request data for verifying the payment.
     * @return [PaymentVerificationDataResponse] containing verification details.
     */
    suspend fun paymentVerify(
        paymentVerifyRequest: PaymentVerifyRequest
    ): PaymentVerificationDataResponse? {
        Validator.validateMerchantId(paymentVerifyRequest.merchantId ?: config.merchantId)
        Validator.validateAuthority(paymentVerifyRequest.authority)
        Validator.validateAmount(paymentVerifyRequest.amount)

        return withContext(Dispatchers.IO) {
            service.paymentVerify(paymentVerifyRequest)
        }
    }

    /**
     * Inquires about a payment status.
     * @param paymentInquiryRequest Request data for the payment inquiry.
     * @return [PaymentInquiryDataResponse] containing inquiry details.
     */
    suspend fun paymentInquiry(
        paymentInquiryRequest: PaymentInquiryRequest
    ): PaymentInquiryDataResponse? {
        Validator.validateMerchantId(paymentInquiryRequest.merchantId ?: config.merchantId)
        Validator.validateAuthority(paymentInquiryRequest.authority)

        return withContext(Dispatchers.IO) {
            service.paymentInquiry(paymentInquiryRequest)
        }
    }


    /**
     * Retrieves unverified payments.
     * @param paymentUnVerifiedRequest Optional request data for retrieving unverified payments.
     * @return [PaymentUnVerifiedDataResponse] containing unverified payment details.
     */
    suspend fun paymentUnVerified(
        paymentUnVerifiedRequest: PaymentUnVerifiedRequest = PaymentUnVerifiedRequest()
    ): PaymentUnVerifiedDataResponse? {
        Validator.validateMerchantId(paymentUnVerifiedRequest.merchantId ?: config.merchantId)

        return withContext(Dispatchers.IO) {
            service.paymentUnVerified(paymentUnVerifiedRequest)
        }
    }

    /**
     * Retrieves unverified payments.
     * @param paymentUnVerifiedRequest Optional request data for retrieving unverified payments.
     * @return [PaymentUnVerifiedDataResponse] containing unverified payment details.
     */
    suspend fun paymentReverse(
        paymentReverseRequest: PaymentReverseRequest
    ): PaymentReverseDataResponse? {
        Validator.validateMerchantId(paymentReverseRequest.merchantId ?: config.merchantId)
        Validator.validateAuthority(paymentReverseRequest.authority)

        return withContext(Dispatchers.IO) {
            service.paymentReverse(paymentReverseRequest)
        }
    }

    /**
     * Retrieves transaction history.
     * @param transactionRequest Request data for fetching transactions.
     * @return A list of [Session] objects containing transaction details.
     */
    suspend fun getTransactions(
        transactionRequest: TransactionRequest
    ): List<Session>? {
        Validator.validateTerminalId(transactionRequest.terminalId)
        Validator.validateLimit(transactionRequest.limit)
        Validator.validateOffset(transactionRequest.offset)

        return withContext(Dispatchers.IO) {
            service.getTransactions(transactionRequest)
        }
    }

    /**
     * Processes a payment refund.
     * @param paymentRefundRequest Request data for refunding the payment.
     * @return [PaymentRefundResponse] containing refund details.
     */
    suspend fun paymentRefund(
        paymentRefundRequest: PaymentRefundRequest
    ): PaymentRefundResponse? {
        Validator.validateSessionId(paymentRefundRequest.sessionId)
        Validator.validateAmount(paymentRefundRequest.amount, minAmount = 20_000)

        return withContext(Dispatchers.IO) {
            service.paymentRefund(paymentRefundRequest)
        }
    }
}