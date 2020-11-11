package com.acme.videorental.usecase.filmtransaction

import com.acme.videorental.domain.model.common.FilmTypeEnum
import com.acme.videorental.domain.model.customer.Customer
import com.acme.videorental.domain.model.customer.CustomerRepository
import com.acme.videorental.domain.model.film.Film
import com.acme.videorental.domain.model.film.FilmId
import com.acme.videorental.domain.model.film.FilmNotFoundException
import com.acme.videorental.domain.model.film.FilmRepository
import com.acme.videorental.domain.model.filmpayment.PaymentTransactionRepository
import com.acme.videorental.domain.model.filmtransaction.FilmTransactionRepository
import com.acme.videorental.infrastructure.repository.customer.DummyCustomerRepository
import com.acme.videorental.infrastructure.repository.film.DummyFilmRepository
import com.acme.videorental.infrastructure.repository.filmtransaction.DummyFilmTransactionRepository
import com.acme.videorental.infrastructure.repository.payments.DummyPaymentTransactionRepository
import com.acme.videorental.usecase.filmtransaction.command.OneFilmRentalCommand
import com.acme.videorental.usecase.filmtransaction.command.RentFilmsCommand
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test
import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class RentFilmsUseCaseShould {

    lateinit var customerRepository: CustomerRepository
    lateinit var filmRepository: FilmRepository
    lateinit var paymentTransactionRepository: PaymentTransactionRepository
    lateinit var filmTransactionRepository: FilmTransactionRepository
    lateinit var filmRentalAssembler: FilmRentalAssembler
    lateinit var selectedFilmView: SelectedFilmView

    @Before
    fun setup() {
        selectedFilmView = InMemorySelectedFilmView(ConcurrentHashMap())
        filmRepository = DummyFilmRepository(mutableListOf())
        customerRepository = DummyCustomerRepository(mutableListOf())
        paymentTransactionRepository = DummyPaymentTransactionRepository(mutableListOf())
        filmTransactionRepository = DummyFilmTransactionRepository(mutableListOf())
        filmRentalAssembler = FilmRentalAssembler(filmRepository, filmTransactionRepository, customerRepository)
    }

    @Test
    fun allow_rent_one_film_if_not_rented_already() {

        val aCustomer = Customer.aNewCustomer(customerRepository.nextIdentity(), "Pablo")
        val aFilm = Film.create(filmRepository.nextIdentity(), "Star wars IV", FilmTypeEnum.OLD)

        val filmsRentedCommand = listOf(OneFilmRentalCommand(aFilm.filmId, 3))

        val rentFilmsCommand = RentFilmsCommand(customerId = aCustomer.customerId, rentFilmCommands = filmsRentedCommand)

        filmRepository.add(aFilm)
        customerRepository.add(aCustomer)

        val rentFilmUseCase = RentFilmsUseCase(selectedFilmView = selectedFilmView,
                filmRepository = filmRepository, customerRepository = customerRepository,
                paymentTransactionRepository = paymentTransactionRepository,
                filmTransactionRepository = filmTransactionRepository,
                filmRentalAssembler = filmRentalAssembler)
        val totalPrice = rentFilmUseCase.execute(rentFilmsCommand)
        Assertions.assertThat(totalPrice).isEqualTo(30)
        Assertions.assertThat(selectedFilmView.isAlreadyRentedByCustomer(aCustomer.customerId, aFilm.filmId)).isTrue()
    }

    @Test(expected = FilmNotFoundException::class )
    fun throw_exception_when_trying_to_rent_a_movie_not_existing() {

        val aCustomer = Customer.aNewCustomer(customerRepository.nextIdentity(), "Pablo")

        val filmsRentedCommand = listOf(OneFilmRentalCommand(FilmId(UUID.randomUUID().toString()), 3))

        val rentFilmsCommand = RentFilmsCommand(customerId = aCustomer.customerId, rentFilmCommands = filmsRentedCommand)

        customerRepository.add(aCustomer)

        val rentFilmUseCase = RentFilmsUseCase(selectedFilmView = selectedFilmView,
                filmRepository = filmRepository, customerRepository = customerRepository,
                paymentTransactionRepository = paymentTransactionRepository,
                filmTransactionRepository = filmTransactionRepository,
                filmRentalAssembler = filmRentalAssembler)
        rentFilmUseCase.execute(rentFilmsCommand)
   }

    @Test(expected = IllegalStateException::class)
    fun throw_exception_when_trying_to_rent_an_already_rented_film() {

        val aCustomer = Customer.aNewCustomer(customerRepository.nextIdentity(), "Pablo")
        val aFilm = Film.create(filmRepository.nextIdentity(), "Star wars IV", FilmTypeEnum.OLD)

        val filmsRentedCommand = listOf(OneFilmRentalCommand(aFilm.filmId, 3))

        val rentFilmsCommand = RentFilmsCommand(customerId = aCustomer.customerId, rentFilmCommands = filmsRentedCommand)

        filmRepository.add(aFilm)
        customerRepository.add(aCustomer)

        val rentFilmUseCase = RentFilmsUseCase(selectedFilmView = selectedFilmView,
                filmRepository = filmRepository, customerRepository = customerRepository,
                paymentTransactionRepository = paymentTransactionRepository,
                filmTransactionRepository = filmTransactionRepository,
                filmRentalAssembler = filmRentalAssembler)
        rentFilmUseCase.execute(rentFilmsCommand)
        rentFilmUseCase.execute(rentFilmsCommand)
    }
}