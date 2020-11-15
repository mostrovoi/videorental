package com.acme.videorental.payments.infrastructure

import com.acme.videorental.payments.domain.Payment
import com.acme.videorental.payments.domain.PaymentId
import com.acme.videorental.payments.domain.PaymentRepository
import java.util.*

class DummyPaymentRepository(private val paymentTransactions: MutableCollection<Payment>) : PaymentRepository {

    override fun nextIdentity(): PaymentId {
        return PaymentId(UUID.randomUUID().toString())
    }

    @Synchronized
    override fun save(paymentTransaction: Payment) {
        paymentTransactions.add(paymentTransaction)
    }

}
