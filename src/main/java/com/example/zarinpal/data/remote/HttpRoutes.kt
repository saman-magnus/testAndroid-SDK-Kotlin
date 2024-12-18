package com.example.zarinpal.data.remote

object HttpRoutes {
    private const val BASE_URL = "https://payment.zarinpal.com"
    private const val BASE_URL_SANDBOX = "https://sandbox.zarinpal.com"
    private const val START_PAY_URL = "/pg/StartPay/"

    const val BASE_URL_GRAPH = "https://next.zarinpal.com/api/v4/graphql"

    private const val PAYMENT = "/pg/v4/payment/request.json"
    private const val PAYMENT_VERIFY = "/pg/v4/payment/verify.json"
    private const val PAYMENT_INQUIRY = "/pg/v4/payment/inquiry.json"
    private const val PAYMENT_UN_VERIFIED = "/pg/v4/payment/unVerified.json"
    private const val PAYMENT_REVERSE = "/pg/v4/payment/reverse.json"

    fun createPayment(sandBox: Boolean): String {
        return (if (sandBox) BASE_URL_SANDBOX else BASE_URL) + PAYMENT
    }

    fun paymentVerify(sandBox: Boolean): String {
        return (if (sandBox) BASE_URL_SANDBOX else BASE_URL) + PAYMENT_VERIFY
    }

    fun paymentInquiry(sandBox: Boolean): String {
        return (if (sandBox) BASE_URL_SANDBOX else BASE_URL) + PAYMENT_INQUIRY
    }

    fun paymentUnVerified(sandBox: Boolean): String {
        return (if (sandBox) BASE_URL_SANDBOX else BASE_URL) + PAYMENT_UN_VERIFIED
    }

    fun paymentReverse(sandBox: Boolean): String {
        return (if (sandBox) BASE_URL_SANDBOX else BASE_URL) + PAYMENT_REVERSE
    }

    fun getRedirectUrl(authority: String, sandBox: Boolean): String {
        if ((authority ?: "").isEmpty()) return ""
        return (if (sandBox) BASE_URL_SANDBOX else BASE_URL) + START_PAY_URL + authority
    }
}