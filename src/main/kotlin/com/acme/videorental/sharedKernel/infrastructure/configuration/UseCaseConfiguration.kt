package com.acme.videorental.sharedKernel.infrastructure.configuration

import com.acme.videorental.customers.application.AddCustomersUseCase
import com.acme.videorental.customers.application.GetAllCustomersUseCase
import com.acme.videorental.customers.domain.CustomerRepository
import com.acme.videorental.payments.domain.PaymentRepository
import com.acme.videorental.purchases.application.AddFilmsUseCase
import com.acme.videorental.purchases.application.GetAllFilmsUseCase
import com.acme.videorental.purchases.domain.FilmRepository
import com.acme.videorental.rentals.application.*
import com.acme.videorental.rentals.domain.RentalRepository
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
    fun rentFilmsUseCase(customerRepository: CustomerRepository, filmRepository: FilmRepository, filmRentalAssembler: FilmRentalAssembler, paymentRepository: PaymentRepository, selectedFilmView: SelectedFilmView, rentalRepository: RentalRepository): RentFilmsUseCase {
        return RentFilmsUseCase(customerRepository = customerRepository, filmRepository = filmRepository, filmRentalAssembler = filmRentalAssembler, selectedFilmView = selectedFilmView, paymentRepository = paymentRepository, rentalRepository = rentalRepository)
    }

    @Bean
    fun returnFilmsUseCase(customerRepository: CustomerRepository, paymentRepository: PaymentRepository, filmReturnAssembler: FilmReturnAssembler, selectedFilmView: SelectedFilmView, rentalRepository: RentalRepository, filmRepository: FilmRepository): ReturnFilmsUseCase {
        return ReturnFilmsUseCase(customerRepository = customerRepository, filmReturnedAssembler = filmReturnAssembler, selectedFilmView = selectedFilmView, paymentRepository = paymentRepository, rentalRepository = rentalRepository, filmRepository = filmRepository)
    }

    @Bean
    fun getAllFilmsUseCase(filmRepository: FilmRepository): GetAllFilmsUseCase {
        return GetAllFilmsUseCase(filmRepository = filmRepository)
    }

    @Bean
    fun getAllCustomersUseCAse(customerRepository: CustomerRepository): GetAllCustomersUseCase {
        return GetAllCustomersUseCase(customerRepository = customerRepository)
    }

    @Bean
    fun addFilmsUseCase(filmRepository: FilmRepository): AddFilmsUseCase {
        return AddFilmsUseCase(filmRepository = filmRepository)
    }

    @Bean
    fun restoreAllFilmsTransactionsFromHistory(rentalRepository: RentalRepository, customerRepository: CustomerRepository, selectedFilmView: SelectedFilmView): ReplayRentalsUseCase {
        return ReplayRentalsUseCase(rentalRepository = rentalRepository, selectedFilmView = selectedFilmView(), customerRepository = customerRepository)
    }

    @Bean
    fun selectedFilmView(): SelectedFilmView {
        return InMemorySelectedFilmView(ConcurrentHashMap())
    }


}