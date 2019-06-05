package com.casumo.videorental.domain.model.filmpayment

interface PaymentTransactionRepository {

    fun save(paymentTransaction: PaymentTransaction)
    fun nextIdentity(): PaymentTransactionId
}