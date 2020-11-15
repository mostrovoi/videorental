package com.acme.videorental.payments.infrastructure

import com.acme.videorental.payments.domain.Payment
import com.acme.videorental.payments.domain.PaymentId
import com.acme.videorental.payments.domain.PaymentRepository
import com.acme.videorental.sharedKernel.infrastructure.jooq.Tables
import org.jooq.DSLContext
import java.sql.Timestamp
import java.util.*

class JdbcPaymentTransactionRepository(private val dslContext: DSLContext) : PaymentRepository {


    override fun nextIdentity(): PaymentId {
        return PaymentId(UUID.randomUUID().toString())
    }

    override fun save(paymentTransaction: Payment) {
        dslContext.insertInto(Tables.PAYMENT_TRANSACTION)
                .columns(Tables.PAYMENT_TRANSACTION.PAYMENT_TRANSACTION_ID,
                        Tables.PAYMENT_TRANSACTION.CUSTOMER_ID,
                        Tables.PAYMENT_TRANSACTION.AMOUNT,
                        Tables.PAYMENT_TRANSACTION.CREATED_ON,
                        Tables.PAYMENT_TRANSACTION.PAYMENT_TYPE
                )
                .values(paymentTransaction.paymentId.anId,
                        paymentTransaction.customerId.anId,
                        paymentTransaction.amount,
                        Timestamp.from(paymentTransaction.createdOn),
                        paymentTransaction.paymentType.name).execute()
    }


}