package com.example.zarinpal.data.remote.dto

data class Config(
    val merchantId: String,
    val token: String? = null,
    val sandBox :Boolean=false
)