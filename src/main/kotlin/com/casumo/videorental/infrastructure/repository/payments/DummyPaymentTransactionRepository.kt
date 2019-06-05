package com.casumo.videorental.infrastructure.repository.payments

import com.casumo.videorental.domain.model.filmpayment.PaymentTransaction
import com.casumo.videorental.domain.model.filmpayment.PaymentTransactionId
import com.casumo.videorental.domain.model.filmpayment.PaymentTransactionRepository
import java.util.*

class DummyPaymentTransactionRepository(private val paymentTransactions: MutableCollection<PaymentTransaction>) : PaymentTransactionRepository {
    override fun nextIdentity(): PaymentTransactionId {
        return PaymentTransactionId(UUID.randomUUID().toString())
    }

    @Synchronized
    override fun save(paymentTransaction: PaymentTransaction) {
        paymentTransactions.add(paymentTransaction)
    }

}
