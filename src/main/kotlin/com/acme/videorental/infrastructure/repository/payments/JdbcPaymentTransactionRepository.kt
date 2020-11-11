package com.acme.videorental.infrastructure.repository.payments

import com.acme.videorental.domain.model.filmpayment.PaymentTransaction
import com.acme.videorental.domain.model.filmpayment.PaymentTransactionId
import com.acme.videorental.domain.model.filmpayment.PaymentTransactionRepository
import com.acme.videorental.infrastructure.repository.jooq.Tables
import org.jooq.DSLContext
import java.sql.Timestamp
import java.util.*

class JdbcPaymentTransactionRepository(private val dslContext: DSLContext) : PaymentTransactionRepository {


    override fun nextIdentity(): PaymentTransactionId {
        return PaymentTransactionId(UUID.randomUUID().toString())
    }

    override fun save(paymentTransaction: PaymentTransaction) {
        dslContext.insertInto(Tables.PAYMENT_TRANSACTION)
                .columns(Tables.PAYMENT_TRANSACTION.PAYMENT_TRANSACTION_ID,
                        Tables.PAYMENT_TRANSACTION.CUSTOMER_ID,
                        Tables.PAYMENT_TRANSACTION.AMOUNT,
                        Tables.PAYMENT_TRANSACTION.CREATED_ON,
                        Tables.PAYMENT_TRANSACTION.PAYMENT_TYPE
                )
                .values(paymentTransaction.paymentTransactionId.anId,
                        paymentTransaction.customerId.anId,
                        paymentTransaction.amount,
                        Timestamp.from(paymentTransaction.createdOn),
                        paymentTransaction.paymentType.name).execute()
    }


}