package com.acme.videorental.rentals.application

import com.acme.videorental.customers.domain.Customer
import com.acme.videorental.customers.domain.CustomerRepository
import com.acme.videorental.customers.infrastructure.DummyCustomerRepository
import com.acme.videorental.payments.domain.PaymentRepository
import com.acme.videorental.payments.infrastructure.DummyPaymentRepository
import com.acme.videorental.purchases.domain.Film
import com.acme.videorental.purchases.domain.FilmNotFoundException
import com.acme.videorental.purchases.domain.FilmRepository
import com.acme.videorental.purchases.infrastructure.DummyFilmRepository
import com.acme.videorental.rentals.application.command.OneFilmRentalCommand
import com.acme.videorental.rentals.application.command.RentFilmsCommand
import com.acme.videorental.rentals.domain.RentalRepository
import com.acme.videorental.rentals.infrastructure.DummyRentalRepository
import com.acme.videorental.sharedKernel.domain.FilmId
import com.acme.videorental.sharedKernel.domain.FilmTypeEnum
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class RentFilmsUseCaseShould {

    lateinit var customerRepository: CustomerRepository
    lateinit var filmRepository: FilmRepository
    lateinit var rentalRepository: RentalRepository
    lateinit var paymentRepository: PaymentRepository
    lateinit var filmRentalAssembler: FilmRentalAssembler
    lateinit var selectedFilmView: SelectedFilmView

    @Before
    fun setup() {
        selectedFilmView = InMemorySelectedFilmView(ConcurrentHashMap())
        filmRepository = DummyFilmRepository(mutableListOf())
        customerRepository = DummyCustomerRepository(mutableListOf())
        paymentRepository = DummyPaymentRepository(mutableListOf())
        rentalRepository = DummyRentalRepository(mutableListOf())
        filmRentalAssembler = FilmRentalAssembler(filmRepository, rentalRepository, customerRepository)
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
                paymentRepository = paymentRepository,
                rentalRepository = rentalRepository,
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
                paymentRepository = paymentRepository,
                rentalRepository = rentalRepository,
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
                paymentRepository = paymentRepository,
                rentalRepository = rentalRepository,
                filmRentalAssembler = filmRentalAssembler)
        rentFilmUseCase.execute(rentFilmsCommand)
        rentFilmUseCase.execute(rentFilmsCommand)
    }
}