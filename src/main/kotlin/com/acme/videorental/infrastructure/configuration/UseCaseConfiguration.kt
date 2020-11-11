package com.acme.videorental.infrastructure.configuration

import com.acme.videorental.domain.model.customer.CustomerRepository
import com.acme.videorental.domain.model.film.FilmRepository
import com.acme.videorental.domain.model.filmpayment.PaymentTransactionRepository
import com.acme.videorental.domain.model.filmtransaction.FilmTransactionRepository
import com.acme.videorental.usecase.customer.AddCustomersUseCase
import com.acme.videorental.usecase.customer.GetAllCustomersUseCase
import com.acme.videorental.usecase.film.AddFilmsUseCase
import com.acme.videorental.usecase.film.GetAllFilmsUseCase
import com.acme.videorental.usecase.filmtransaction.*
import com.acme.videorental.usecase.replay.ReplayFilmTransactionsUseCase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.ConcurrentHashMap

@Configuration
class UseCaseConfiguration {

    @Bean
    fun addCustomersUseCase(customerRepository: CustomerRepository): AddCustomersUseCase {
        return AddCustomersUseCase(customerRepository = customerRepository)
    }

    @Bean
    fun rentFilmsUseCase(customerRepository: CustomerRepository, filmRepository: FilmRepository, filmRentalAssembler: FilmRentalAssembler, paymentTransactionRepository: PaymentTransactionRepository, selectedFilmView: SelectedFilmView, filmTransactionRepository: FilmTransactionRepository): RentFilmsUseCase {
        return RentFilmsUseCase(customerRepository = customerRepository, filmRepository = filmRepository, filmRentalAssembler = filmRentalAssembler, selectedFilmView = selectedFilmView, paymentTransactionRepository = paymentTransactionRepository, filmTransactionRepository = filmTransactionRepository)
    }

    @Bean
    fun returnFilmsUseCase(customerRepository: CustomerRepository, paymentTransactionRepository: PaymentTransactionRepository, filmReturnAssembler: FilmReturnAssembler, selectedFilmView: SelectedFilmView, filmTransactionRepository: FilmTransactionRepository, filmRepository: FilmRepository): ReturnFilmsUseCase {
        return ReturnFilmsUseCase(customerRepository = customerRepository, filmReturnedAssembler = filmReturnAssembler, selectedFilmView = selectedFilmView, paymentTransactionRepository = paymentTransactionRepository, filmTransactionRepository = filmTransactionRepository, filmRepository = filmRepository)
    }

    @Bean
    fun getAllFilmsUseCase(filmRepository: FilmRepository, filmTransactionRepository: FilmTransactionRepository): GetAllFilmsUseCase {
        return GetAllFilmsUseCase(filmRepository = filmRepository)
    }

    @Bean
    fun getAllCustomersUseCAse(customerRepository: CustomerRepository, filmTransactionRepository: FilmTransactionRepository): GetAllCustomersUseCase {
        return GetAllCustomersUseCase(customerRepository = customerRepository)
    }

    @Bean
    fun addFilmsUseCase(filmRepository: FilmRepository): AddFilmsUseCase {
        return AddFilmsUseCase(filmRepository = filmRepository)
    }

    @Bean
    fun restoreAllFilmsTransactionsFromHistory(filmTransactionRepository: FilmTransactionRepository, customerRepository: CustomerRepository, selectedFilmView: SelectedFilmView): ReplayFilmTransactionsUseCase {
        return ReplayFilmTransactionsUseCase(filmTransactionRepository = filmTransactionRepository, selectedFilmView = selectedFilmView(), customerRepository = customerRepository)
    }

    @Bean
    fun selectedFilmView(): SelectedFilmView {
        return InMemorySelectedFilmView(ConcurrentHashMap())
    }


}