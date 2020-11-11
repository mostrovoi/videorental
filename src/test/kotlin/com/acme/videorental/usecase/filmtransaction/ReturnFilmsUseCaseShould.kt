package com.acme.videorental.usecase.filmtransaction

import com.acme.videorental.domain.model.common.FilmTypeEnum
import com.acme.videorental.domain.model.customer.Customer
import com.acme.videorental.domain.model.customer.CustomerRepository
import com.acme.videorental.domain.model.film.Film
import com.acme.videorental.domain.model.film.FilmRepository
import com.acme.videorental.domain.model.filmpayment.PaymentTransactionRepository
import com.acme.videorental.domain.model.filmtransaction.FilmActionEnum
import com.acme.videorental.domain.model.filmtransaction.FilmRented
import com.acme.videorental.domain.model.filmtransaction.FilmTransaction
import com.acme.videorental.domain.model.filmtransaction.FilmTransactionRepository
import com.acme.videorental.infrastructure.repository.customer.DummyCustomerRepository
import com.acme.videorental.infrastructure.repository.film.DummyFilmRepository
import com.acme.videorental.infrastructure.repository.filmtransaction.DummyFilmTransactionRepository
import com.acme.videorental.infrastructure.repository.payments.DummyPaymentTransactionRepository
import com.acme.videorental.usecase.filmtransaction.command.OneFilmReturnCommand
import com.acme.videorental.usecase.filmtransaction.command.ReturnFilmsCommand
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.concurrent.ConcurrentHashMap

class ReturnFilmsUseCaseShould {

    lateinit var customerRepository: CustomerRepository
    lateinit var filmRepository: FilmRepository
    lateinit var paymentTransactionRepository: PaymentTransactionRepository
    lateinit var filmTransactionRepository: FilmTransactionRepository
    lateinit var filmReturnAssembler: FilmReturnAssembler
    lateinit var selectedFilmView: SelectedFilmView

    @Before
    fun setup() {
        selectedFilmView = InMemorySelectedFilmView(ConcurrentHashMap())
        filmRepository = DummyFilmRepository(mutableListOf())
        customerRepository = DummyCustomerRepository(mutableListOf())
        paymentTransactionRepository = DummyPaymentTransactionRepository(mutableListOf())
        filmTransactionRepository = DummyFilmTransactionRepository(mutableListOf())
        filmReturnAssembler = FilmReturnAssembler(filmRepository, filmTransactionRepository, customerRepository)
    }

    @Test
    fun allow_return_one_rented_film() {
        val aCustomer = Customer.aNewCustomer(customerRepository.nextIdentity(), "Pablo")
        val aFilm = Film.create(filmRepository.nextIdentity(), "Avengers", FilmTypeEnum.NEW)

        val filmsReturnedCommand = listOf(OneFilmReturnCommand(aFilm.filmId))
        val returnFilmsCommand = ReturnFilmsCommand(customerId = aCustomer.customerId, returnFilmCommands = filmsReturnedCommand)
        filmRepository.add(aFilm)

        val rentalTime = Instant.now().minusSeconds(3600)
        val filmTransactionId = filmTransactionRepository.nextIdentity()
        filmTransactionRepository.save(FilmTransaction(filmTransactionId, aFilm.filmId, aCustomer.customerId, 1, FilmActionEnum.RENT, rentalTime))
        selectedFilmView.rentFilm(FilmRented(filmTransactionId, aFilm.filmId, aCustomer.customerId, 1, rentalTime))
        customerRepository.add(aCustomer)
        val returnFilmsUseCase = ReturnFilmsUseCase(selectedFilmView = selectedFilmView,
                filmRepository = filmRepository, customerRepository = customerRepository,
                filmTransactionRepository = filmTransactionRepository,
                paymentTransactionRepository = paymentTransactionRepository,
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
                filmTransactionRepository = filmTransactionRepository,
                paymentTransactionRepository = paymentTransactionRepository,
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
        val filmTransactionId = filmTransactionRepository.nextIdentity()

        val rentalTime = Instant.now().minus(7, ChronoUnit.DAYS)

        filmTransactionRepository.save(FilmTransaction(filmTransactionId, aFilm.filmId, aCustomer.customerId,
                3, FilmActionEnum.RENT, rentalTime))
        selectedFilmView.rentFilm(FilmRented(filmTransactionId, aFilm.filmId, aCustomer.customerId, 1, rentalTime))
        customerRepository.add(aCustomer)
        val returnFilmsUseCase = ReturnFilmsUseCase(selectedFilmView = selectedFilmView,
                filmRepository = filmRepository, customerRepository = customerRepository,
                filmTransactionRepository = filmTransactionRepository,
                paymentTransactionRepository = paymentTransactionRepository,
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
        val filmTransactionId = filmTransactionRepository.nextIdentity()
        val rentalTime = Instant.now().minusSeconds((3600 * 24) + 1)
        filmTransactionRepository.save(FilmTransaction(filmTransactionId, aFilm.filmId, aCustomer.customerId,
                3, FilmActionEnum.RENT, rentalTime))
        selectedFilmView.rentFilm(FilmRented(filmTransactionId, aFilm.filmId, aCustomer.customerId, 1, rentalTime))
        customerRepository.add(aCustomer)
        val returnFilmsUseCase = ReturnFilmsUseCase(selectedFilmView = selectedFilmView,
                filmRepository = filmRepository, customerRepository = customerRepository,
                filmTransactionRepository = filmTransactionRepository,
                paymentTransactionRepository = paymentTransactionRepository,
                filmReturnedAssembler = filmReturnAssembler)

        val overdueCharge = returnFilmsUseCase.execute(returnFilmsCommand)
        Assertions.assertThat(overdueCharge).isEqualTo(40)
        Assertions.assertThat(selectedFilmView.isAvailable(aFilm.filmId)).isTrue()
    }
}