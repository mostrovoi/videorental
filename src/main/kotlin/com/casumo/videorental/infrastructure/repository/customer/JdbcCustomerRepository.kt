package com.casumo.videorental.infrastructure.repository.customer

import com.casumo.videorental.domain.model.customer.Customer
import com.casumo.videorental.domain.model.customer.CustomerId
import com.casumo.videorental.domain.model.customer.CustomerNotFoundException
import com.casumo.videorental.domain.model.customer.CustomerRepository
import com.casumo.videorental.infrastructure.repository.jooq.Tables.CUSTOMER
import mu.KotlinLogging
import org.jooq.DSLContext
import java.sql.Timestamp
import java.util.*

class JdbcCustomerRepository(private val dslContext: DSLContext, private val jdbcCustomerMapper: JdbcCustomerMapper) : CustomerRepository {

    private val logger = KotlinLogging.logger {}

    override fun findByOrFail(customerId: CustomerId): Customer {
        return dslContext.selectFrom(CUSTOMER).where(CUSTOMER.CUSTOMER_ID.eq(customerId.anId)).fetchOptional(jdbcCustomerMapper).toNullable()
                ?: throw CustomerNotFoundException("${customerId.anId}")
    }

    override fun getAll(): Collection<Customer> {
        return dslContext.selectFrom(CUSTOMER).fetch(jdbcCustomerMapper)
    }

    override fun update(customer: Customer): Boolean {
        dslContext.update(CUSTOMER)
                .set(CUSTOMER.BONUS_CARD_POINTS, customer.bonusCard.bonusPoints)
                .set(CUSTOMER.NAME, customer.name)
                .set(CUSTOMER.UPDATED_ON, Timestamp.from(customer.updatedOn))
                .where(CUSTOMER.CUSTOMER_ID.eq(customer.customerId.anId)).execute()
        logger.info { "Updated customer with ID: ${customer.customerId.anId}" }
        return true
    }

    override fun add(customer: Customer): Boolean {
        dslContext.insertInto(CUSTOMER)
                .columns(CUSTOMER.CUSTOMER_ID, CUSTOMER.NAME,
                        CUSTOMER.BONUS_CARD_POINTS, CUSTOMER.CREATED_ON,
                        CUSTOMER.UPDATED_ON)
                .values(customer.customerId.anId, customer.name,
                        customer.bonusCard.bonusPoints, Timestamp.from(customer.createdOn),
                        Timestamp.from(customer.updatedOn)).execute()
        logger.info { "Added a new customer with ID: ${customer.customerId.anId} and name ${customer.name}" }
        return true

    }

    override fun nextIdentity(): CustomerId {
        return CustomerId(UUID.randomUUID().toString())
    }

    fun <T : Any> Optional<T>.toNullable(): T? = this.orElse(null)

}
