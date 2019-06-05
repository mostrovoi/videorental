package com.casumo.videorental.infrastructure.configuration

import com.casumo.videorental.domain.model.customer.CustomerRepository
import com.casumo.videorental.domain.model.film.FilmRepository
import com.casumo.videorental.domain.model.filmpayment.PaymentTransactionRepository
import com.casumo.videorental.domain.model.filmtransaction.FilmTransactionRepository
import com.casumo.videorental.infrastructure.repository.customer.JdbcCustomerMapper
import com.casumo.videorental.infrastructure.repository.customer.JdbcCustomerRepository
import com.casumo.videorental.infrastructure.repository.film.JdbcFilmMapper
import com.casumo.videorental.infrastructure.repository.film.JdbcFilmRepository
import com.casumo.videorental.infrastructure.repository.filmtransaction.JdbcFilmTransactionMapper
import com.casumo.videorental.infrastructure.repository.filmtransaction.JdbcFilmTransactionRepository
import com.casumo.videorental.infrastructure.repository.payments.JdbcPaymentTransactionRepository
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