package com.acme.videorental.payments.domain

interface PaymentRepository {
    fun save(paymentTransaction: Payment)
    fun nextIdentity(): PaymentId
}