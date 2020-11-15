package com.acme.videorental.rentals.application

import com.acme.videorental.customers.domain.Customer
import com.acme.videorental.customers.domain.CustomerRepository
import com.acme.videorental.customers.infrastructure.DummyCustomerRepository
import com.acme.videorental.payments.domain.PaymentRepository
import com.acme.videorental.payments.infrastructure.DummyPaymentRepository
import com.acme.videorental.purchases.domain.Film
import com.acme.videorental.sharedKernel.domain.FilmTypeEnum
import com.acme.videorental.purchases.domain.FilmRepository
import com.acme.videorental.purchases.infrastructure.DummyFilmRepository

import com.acme.videorental.rentals.application.command.OneFilmReturnCommand
import com.acme.videorental.rentals.application.command.ReturnFilmsCommand
import com.acme.videorental.rentals.domain.FilmRented
import com.acme.videorental.rentals.domain.Rental
import com.acme.videorental.rentals.domain.RentalRepository
import com.acme.videorental.rentals.infrastructure.DummyRentalRepository
import com.acme.videorental.sharedKernel.domain.FilmActionEnum
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.concurrent.ConcurrentHashMap

class ReturnFilmsUseCaseShould {

    lateinit var customerRepository: CustomerRepository
    lateinit var filmRepository: FilmRepository
    lateinit var paymentRepository: PaymentRepository
    lateinit var rentalRepository: RentalRepository
    lateinit var filmReturnAssembler: FilmReturnAssembler
    lateinit var selectedFilmView: SelectedFilmView

    @Before
    fun setup() {
        selectedFilmView = InMemorySelectedFilmView(ConcurrentHashMap())
        filmRepository = DummyFilmRepository(mutableListOf())
        customerRepository = DummyCustomerRepository(mutableListOf())
        paymentRepository = DummyPaymentRepository(mutableListOf())
        rentalRepository = DummyRentalRepository(mutableListOf())
        filmReturnAssembler = FilmReturnAssembler(filmRepository, rentalRepository, customerRepository)
    }

    @Test
    fun allow_return_one_rented_film() {
        val aCustomer = Customer.aNewCustomer(customerRepository.nextIdentity(), "Pablo")
        val aFilm = Film.create(filmRepository.nextIdentity(), "Avengers", FilmTypeEnum.NEW)

        val filmsReturnedCommand = listOf(OneFilmReturnCommand(aFilm.filmId))
        val returnFilmsCommand = ReturnFilmsCommand(customerId = aCustomer.customerId, returnFilmCommands = filmsReturnedCommand)
        filmRepository.add(aFilm)

        val rentalTime = Instant.now().minusSeconds(3600)
        val rentalId = rentalRepository.nextIdentity()
        rentalRepository.save(Rental(rentalId, aFilm.filmId, aCustomer.customerId, 1, FilmActionEnum.RENT, rentalTime))
        selectedFilmView.rentFilm(FilmRented(rentalId, aFilm.filmId, aCustomer.customerId, 1, rentalTime))
        customerRepository.add(aCustomer)
        val returnFilmsUseCase = ReturnFilmsUseCase(selectedFilmView = selectedFilmView,
                filmRepository = filmRepository, customerRepository = customerRepository,
                rentalRepository = rentalRepository,
                paymentRepository = paymentRepository,
                filmReturnedAssembler = filmReturnAssembler)
        val overdueCharge = returnFilmsUseCase.execute(returnFilmsCommand)
        Assertions.assertThat(overdueCharge).isEqualTo(0)
        Assertions.assertThat(selectedFilmView.isAvailable(aFilm.filmId)).isTrue()
    }

    @Test(expected = IllegalArgumentException::class)
    fun throw_exception_when_trying_to_return_non_rented_film() {
        val aCustomer = Customer.aNewCustomer(customerRepository.nextIdentity(), "Pablo")
        val aFilm = Film.create(filmRepository.nextIdentity(), "Star wars IV", FilmTypeEnum.OLD)

        val filmsReturnedCommand = listOf(OneFilmReturnCommand(aFilm.filmId))
        val returnFilmsCommand = ReturnFilmsCommand(customerId = aCustomer.customerId, returnFilmCommands = filmsReturnedCommand)
        filmRepository.add(aFilm)
        customerRepository.add(aCustomer)
        val returnFilmsUseCase = ReturnFilmsUseCase(selectedFilmView = selectedFilmView,
                filmRepository = filmRepository, customerRepository = customerRepository,
                rentalRepository = rentalRepository,
                paymentRepository = paymentRepository,
                filmReturnedAssembler = filmReturnAssembler)
        returnFilmsUseCase.execute(returnFilmsCommand)
    }

    @Test
    fun calculate_surcharge_overdue_for_late_return() {
        val aCustomer = Customer.aNewCustomer(customerRepository.nextIdentity(), "Pablo")
        val aFilm = Film.create(filmRepository.nextIdentity(), "Avengers", FilmTypeEnum.REGULAR)

        val filmsReturnedCommand = listOf(OneFilmReturnCommand(aFilm.filmId))
        val returnFilmsCommand = ReturnFilmsCommand(customerId = aCustomer.customerId, returnFilmCommands = filmsReturnedCommand)
        filmRepository.add(aFilm)
        val rentalId = rentalRepository.nextIdentity()

        val rentalTime = Instant.now().minus(7, ChronoUnit.DAYS)

        rentalRepository.save(Rental(rentalId, aFilm.filmId, aCustomer.customerId,
                3, FilmActionEnum.RENT, rentalTime))
        selectedFilmView.rentFilm(FilmRented(rentalId, aFilm.filmId, aCustomer.customerId, 1, rentalTime))
        customerRepository.add(aCustomer)
        val returnFilmsUseCase = ReturnFilmsUseCase(selectedFilmView = selectedFilmView,
                filmRepository = filmRepository, customerRepository = customerRepository,
                rentalRepository = rentalRepository,
                paymentRepository = paymentRepository,
                filmReturnedAssembler = filmReturnAssembler)
        val overdueCharge = returnFilmsUseCase.execute(returnFilmsCommand)
        Assertions.assertThat(overdueCharge).isEqualTo(30 * 5)
        Assertions.assertThat(selectedFilmView.isAvailable(aFilm.filmId)).isTrue()
    }

    @Test
    fun calculate_surcharge_for_one_second_overdue() {
        val aCustomer = Customer.aNewCustomer(customerRepository.nextIdentity(), "Pablo")
        val aFilm = Film.create(filmRepository.nextIdentity(), "Alladin", FilmTypeEnum.NEW)

        val filmsReturnedCommand = listOf(OneFilmReturnCommand(aFilm.filmId))
        val returnFilmsCommand = ReturnFilmsCommand(customerId = aCustomer.customerId, returnFilmCommands = filmsReturnedCommand)
        filmRepository.add(aFilm)
        val rentalId = rentalRepository.nextIdentity()
        val rentalTime = Instant.now().minusSeconds((3600 * 24) + 1)
        rentalRepository.save(Rental(rentalId, aFilm.filmId, aCustomer.customerId,
                3, FilmActionEnum.RENT, rentalTime))
        selectedFilmView.rentFilm(FilmRented(rentalId, aFilm.filmId, aCustomer.customerId, 1, rentalTime))
        customerRepository.add(aCustomer)
        val returnFilmsUseCase = ReturnFilmsUseCase(selectedFilmView = selectedFilmView,
                filmRepository = filmRepository, customerRepository = customerRepository,
                rentalRepository = rentalRepository,
                paymentRepository = paymentRepository,
                filmReturnedAssembler = filmReturnAssembler)

        val overdueCharge = returnFilmsUseCase.execute(returnFilmsCommand)
        Assertions.assertThat(overdueCharge).isEqualTo(40)
        Assertions.assertThat(selectedFilmView.isAvailable(aFilm.filmId)).isTrue()
    }
}