package com.acme.videorental.infrastructure.configuration

import com.acme.videorental.domain.model.customer.CustomerRepository
import com.acme.videorental.domain.model.film.FilmRepository
import com.acme.videorental.domain.model.filmpayment.PaymentTransactionRepository
import com.acme.videorental.domain.model.filmtransaction.FilmTransactionRepository
import com.acme.videorental.infrastructure.repository.customer.JdbcCustomerMapper
import com.acme.videorental.infrastructure.repository.customer.JdbcCustomerRepository
import com.acme.videorental.infrastructure.repository.film.JdbcFilmMapper
import com.acme.videorental.infrastructure.repository.film.JdbcFilmRepository
import com.acme.videorental.infrastructure.repository.filmtransaction.JdbcFilmTransactionMapper
import com.acme.videorental.infrastructure.repository.filmtransaction.JdbcFilmTransactionRepository
import com.acme.videorental.infrastructure.repository.payments.JdbcPaymentTransactionRepository
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
    fun filmTransactionRepository(dslContext: DSLContext, jdbcFilmTransactionMapper: JdbcFilmTransactionMapper): FilmTransactionRepository {
        return JdbcFilmTransactionRepository(dslContext, jdbcFilmTransactionMapper)
    }

    @Bean
    fun paymentTransactionRepository(dslContext: DSLContext): PaymentTransactionRepository {
        return JdbcPaymentTransactionRepository(dslContext)
    }
}