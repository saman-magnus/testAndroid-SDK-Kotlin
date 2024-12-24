package com.example.zarinpal.utils

/**
 * This object contains functions for validating various input fields used in the ZarinPal payment gateway system.
 */
class Validator {

    companion object {

        /**
         * Validates the provided merchant ID. It must be in the correct UUID format.
         *
         * @param merchantId The merchant ID to validate.
         * @throws IllegalArgumentException if the merchant ID is null or invalid.
         */
        fun validateMerchantId(merchantId: String) {
            val pattern = "^[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}$".toRegex()
            if (!pattern.matches(merchantId)) {
                throw IllegalArgumentException("Invalid merchant_id format. It should be a valid UUID.")
            }
        }

        /**
         * Validates the authority string. It must start with 'A' or 'S' followed by 35 alphanumeric characters.
         *
         * @param authority The authority string to validate.
         * @throws IllegalArgumentException if the authority format is invalid.
         */
        fun validateAuthority(authority: String) {
            val pattern = "^[AS][0-9a-zA-Z]{35}$".toRegex()
            if (!pattern.matches(authority)) {
                throw IllegalArgumentException("Invalid authority format. It should be a string starting with 'A' or 'S' followed by 35 alphanumeric characters.")
            }
        }

        /**
         * Validates the payment amount. It must be greater than or equal to the minimum amount.
         *
         * @param amount The amount to validate.
         * @param minAmount The minimum allowed amount (default is 1000).
         * @throws IllegalArgumentException if the amount is less than the minimum.
         */
        fun validateAmount(amount: Int, minAmount: Int = 1000) {
            if (amount < minAmount) {
                throw IllegalArgumentException("Amount must be at least $minAmount.")
            }
        }

        /**
         * Validates the callback URL. It must start with "http://" or "https://".
         *
         * @param callbackUrl The callback URL to validate.
         * @throws IllegalArgumentException if the callback URL format is invalid.
         */
        fun validateCallbackUrl(callbackUrl: String) {
            val pattern = "^https?:\\/\\/.*$".toRegex()
            if (!pattern.matches(callbackUrl)) {
                throw IllegalArgumentException("Invalid callback URL format. It should start with http:// or https://.")
            }
        }

        /**
         * Validates the mobile number. It must start with "09" and contain 11 digits.
         *
         * @param mobile The mobile number to validate.
         * @throws IllegalArgumentException if the mobile number is invalid.
         */
        fun validateMobile(mobile: String?) {
            val pattern = "^09[0-9]{9}$".toRegex()
            if (mobile != null && !pattern.matches(mobile)) {
                throw IllegalArgumentException("Invalid mobile number format.")
            }
        }

        /**
         * Validates the email address. It must follow a valid email format.
         *
         * @param email The email address to validate.
         * @throws IllegalArgumentException if the email format is invalid.
         */
        fun validateEmail(email: String?) {
            val pattern = "^[^\\s@]+@[^(\\s@)]+\\.[^\\s@]+$".toRegex()
            if (email != null && !pattern.matches(email)) {
                throw IllegalArgumentException("Invalid email format.")
            }
        }

        /**
         * Validates the currency. Allowed values are "IRR" or "IRT".
         *
         * @param currency The currency to validate.
         * @throws IllegalArgumentException if the currency is not in the allowed list.
         */
        fun validateCurrency(currency: String?) {
            val validCurrencies = listOf("IRR", "IRT")
            if (currency != null && currency !in validCurrencies) {
                throw IllegalArgumentException("Invalid currency format. Allowed values are 'IRR' or 'IRT'.")
            }
        }

        /**
         * Validates the wages list. Each wage must include a valid IBAN, positive amount, and a description shorter than 255 characters.
         *
         * @param wages The list of wages to validate.
         * @throws IllegalArgumentException if any of the wage entries is invalid.
         */
        fun validateWages(wages: List<Map<String, Any>>?) {
            wages?.forEach { wage ->
                val iban = wage["iban"] as String
                val amount = wage["amount"] as Double
                val description = wage["description"] as String

                val ibanPattern = "^[A-Z]{2}[0-9]{2}[0-9A-Z]{1,30}$".toRegex()
                if (!ibanPattern.matches(iban)) {
                    throw IllegalArgumentException("Invalid IBAN format in wages.")
                }
                if (amount <= 0) {
                    throw IllegalArgumentException("Wage amount must be greater than zero.")
                }
                if (description.length > 255) {
                    throw IllegalArgumentException("Wage description must be provided and less than 255 characters.")
                }
            }
        }

        /**
         * Validates the terminal ID. It must contain only digits and cannot be empty.
         *
         * @param terminalId The terminal ID to validate.
         * @throws IllegalArgumentException if the terminal ID is empty or contains non-digit characters.
         */
        fun validateTerminalId(terminalId: String) {
            val regex = Regex("^[0-9]+$") // Matches only digits.
            if (terminalId.isEmpty() || !regex.matches(terminalId)) {
                throw IllegalArgumentException("Terminal ID must contain only digits and cannot be empty.")
            }
        }

        /**
         * Validates the session ID to ensure it is not empty.
         *
         * @param sessionId The session ID to validate.
         * @throws IllegalArgumentException if the session ID is empty.
         */
        fun validateSessionId(sessionId: String) {
            val regex = Regex("^[0-9]+$") // Matches only digits.
            if (sessionId.isEmpty() || !regex.matches(sessionId)) {
                throw IllegalArgumentException("Session ID is required.")
            }
        }

        /**
         * Validates the filter value to ensure it is one of the allowed predefined options.
         *
         * @param filter The filter value to validate.
         * @throws IllegalArgumentException if the filter value is invalid.
         */
        fun validateFilter(filter: String) {
            val validFilters = listOf("ALL","PAID", "VERIFIED", "TRASH", "ACTIVE", "REFUNDED")
            if (filter !in validFilters) {
                throw IllegalArgumentException("Invalid filter value.")
            }
        }

        /**
         * Validates the limit value to ensure it is a positive integer.
         *
         * @param limit The limit value to validate.
         * @throws IllegalArgumentException if the limit is not positive.
         */
        fun validateLimit(limit: Int) {
            if (limit <= 0) {
                throw IllegalArgumentException("Limit must be a positive integer.")
            }
        }

        /**
         * Validates the offset value to ensure it is a non-negative integer.
         *
         * @param offset The offset value to validate.
         * @throws IllegalArgumentException if the offset is negative.
         */
        fun validateOffset(offset: Int) {
            if (offset < 0) {
                throw IllegalArgumentException("Offset must be a non-negative integer.")
            }
        }

        /**
         * Validates the card PAN (Primary Account Number) to ensure it is a 16-digit number.
         *
         * @param cardPan The card PAN value to validate.
         * @throws IllegalArgumentException if the card PAN format is invalid.
         */
        fun validateCardPan(cardPan: String?) {
            val pattern = "^[0-9]{16}$".toRegex()
            if (cardPan != null && !pattern.matches(cardPan)) {
                throw IllegalArgumentException("Invalid card PAN format. It should be a 16-digit number.")
            }
        }

        /**
         * Validates the method to ensure it is one of the allowed options ('PAYA' or 'CARD').
         *
         * @param method The method to validate.
         * @throws IllegalArgumentException if the method is invalid.
         */
        fun validateMethod(method: String) {
            val validMethods = listOf("PAYA", "CARD")
            if (method !in validMethods) {
                throw IllegalArgumentException("Invalid method. Allowed values are 'PAYA' or 'CARD'.")
            }
        }

        /**
         * Validates the reason to ensure it is one of the allowed predefined options.
         *
         * @param reason The reason to validate.
         * @throws IllegalArgumentException if the reason is invalid.
         */
        fun validateReason(reason: String) {
            val validReasons = listOf("CUSTOMER_REQUEST", "DUPLICATE_TRANSACTION", "SUSPICIOUS_TRANSACTION", "OTHER")
            if (reason !in validReasons) {
                throw IllegalArgumentException("Invalid reason. Allowed values are 'CUSTOMER_REQUEST', 'DUPLICATE_TRANSACTION', 'SUSPICIOUS_TRANSACTION', or 'OTHER'.")
            }
        }
    }
}
