package com.casumo.videorental.infrastructure.configuration

import com.casumo.videorental.domain.model.customer.CustomerRepository
import com.casumo.videorental.domain.model.film.FilmRepository
import com.casumo.videorental.domain.model.filmpayment.PaymentTransactionRepository
import com.casumo.videorental.domain.model.filmtransaction.FilmTransactionRepository
import com.casumo.videorental.usecase.customer.AddCustomersUseCase
import com.casumo.videorental.usecase.customer.DefaultAddCustomersUseCase
import com.casumo.videorental.usecase.customer.DefaultGetAllCustomersUseCase
import com.casumo.videorental.usecase.customer.GetAllCustomersUseCase
import com.casumo.videorental.usecase.film.AddFilmsUseCase
import com.casumo.videorental.usecase.film.DefaultAddFilmsUseCase
import com.casumo.videorental.usecase.film.DefaultGetAllFilmsUseCase
import com.casumo.videorental.usecase.film.GetAllFilmsUseCase
import com.casumo.videorental.usecase.filmtransaction.*
import com.casumo.videorental.usecase.replay.DefaultReplayFilmTransactionsUseCase
import com.casumo.videorental.usecase.replay.ReplayFilmTransactionsUseCase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.ConcurrentHashMap

@Configuration
class UseCaseConfiguration {

    @Bean
    fun addCustomersUseCase(customerRepository: CustomerRepository): AddCustomersUseCase {
        return DefaultAddCustomersUseCase(customerRepository = customerRepository)
    }

    @Bean
    fun rentFilmsUseCase(customerRepository: CustomerRepository, filmRepository: FilmRepository, filmRentalAssembler: FilmRentalAssembler, paymentTransactionRepository: PaymentTransactionRepository, selectedFilmView: SelectedFilmView, filmTransactionRepository: FilmTransactionRepository): RentFilmsUseCase {
        return DefaultRentFilmsUseCase(customerRepository = customerRepository, filmRepository = filmRepository, filmRentalAssembler = filmRentalAssembler, selectedFilmView = selectedFilmView, paymentTransactionRepository = paymentTransactionRepository, filmTransactionRepository = filmTransactionRepository)
    }

    @Bean
    fun returnFilmsUseCase(customerRepository: CustomerRepository, paymentTransactionRepository: PaymentTransactionRepository, filmReturnAssembler: FilmReturnAssembler, selectedFilmView: SelectedFilmView, filmTransactionRepository: FilmTransactionRepository, filmRepository: FilmRepository): ReturnFilmsUseCase {
        return DefaultReturnFilmsUseCase(customerRepository = customerRepository, filmReturnedAssembler = filmReturnAssembler, selectedFilmView = selectedFilmView, paymentTransactionRepository = paymentTransactionRepository, filmTransactionRepository = filmTransactionRepository, filmRepository = filmRepository)
    }

    @Bean
    fun getAllFilmsUseCase(filmRepository: FilmRepository, filmTransactionRepository: FilmTransactionRepository): GetAllFilmsUseCase {
        return DefaultGetAllFilmsUseCase(filmRepository = filmRepository)
    }

    @Bean
    fun getAllCustomersUseCAse(customerRepository: CustomerRepository, filmTransactionRepository: FilmTransactionRepository): GetAllCustomersUseCase {
        return DefaultGetAllCustomersUseCase(customerRepository = customerRepository)
    }

    @Bean
    fun addFilmsUseCase(filmRepository: FilmRepository): AddFilmsUseCase {
        return DefaultAddFilmsUseCase(filmRepository = filmRepository)
    }

    @Bean
    fun restoreAllFilmsTransactionsFromHistory(filmTransactionRepository: FilmTransactionRepository, customerRepository: CustomerRepository, selectedFilmView: SelectedFilmView): ReplayFilmTransactionsUseCase {
        return DefaultReplayFilmTransactionsUseCase(filmTransactionRepository = filmTransactionRepository, selectedFilmView = selectedFilmView(), customerRepository = customerRepository)
    }

    @Bean
    fun selectedFilmView(): SelectedFilmView {
        return InMemorySelectedFilmView(ConcurrentHashMap())
    }


}