package com.acme.videorental.sharedKernel.infrastructure.configuration

import com.acme.videorental.customers.domain.CustomerRepository
import com.acme.videorental.customers.infrastructure.JdbcCustomerMapper
import com.acme.videorental.customers.infrastructure.JdbcCustomerRepository
import com.acme.videorental.payments.domain.PaymentRepository
import com.acme.videorental.payments.infrastructure.JdbcPaymentTransactionRepository
import com.acme.videorental.purchases.domain.FilmRepository
import com.acme.videorental.purchases.infrastructure.JdbcFilmMapper
import com.acme.videorental.purchases.infrastructure.JdbcFilmRepository
import com.acme.videorental.rentals.domain.RentalRepository
import com.acme.videorental.rentals.infrastructure.JdbcRentalMapper
import com.acme.videorental.rentals.infrastructure.JdbcRentalRepository
import org.jooq.DSLContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RepositoryConfiguration {

    @Bean
    fun filmRepository(dslContext: DSLContext, jdbcFilmMapper: JdbcFilmMapper): FilmRepository {
        return JdbcFilmRepository(dslContext, jdbcFilmMapper)
    }

    @Bean
    fun customerRepository(dslContext: DSLContext, jdbcCustomerMapper: JdbcCustomerMapper): CustomerRepository {
        return JdbcCustomerRepository(dslContext, jdbcCustomerMapper)
    }

    @Bean
    fun rentalRepository(dslContext: DSLContext, jdbcRentalMapper: JdbcRentalMapper): RentalRepository {
        return JdbcRentalRepository(dslContext, jdbcRentalMapper)
    }

    @Bean
    fun paymentRepository(dslContext: DSLContext): PaymentRepository {
        return JdbcPaymentTransactionRepository(dslContext)
    }
}