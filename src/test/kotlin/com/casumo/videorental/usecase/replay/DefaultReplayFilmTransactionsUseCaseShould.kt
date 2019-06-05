package com.casumo.videorental.usecase.replay

import com.casumo.videorental.domain.model.customer.Customer
import com.casumo.videorental.domain.model.customer.CustomerId
import com.casumo.videorental.domain.model.customer.CustomerRepository
import com.casumo.videorental.domain.model.film.FilmId
import com.casumo.videorental.domain.model.filmtransaction.FilmActionEnum
import com.casumo.videorental.domain.model.filmtransaction.FilmTransaction
import com.casumo.videorental.domain.model.filmtransaction.FilmTransactionId
import com.casumo.videorental.domain.model.filmtransaction.FilmTransactionRepository
import com.casumo.videorental.infrastructure.repository.customer.DummyCustomerRepository
import com.casumo.videorental.infrastructure.repository.filmtransaction.DummyFilmTransactionRepository
import com.casumo.videorental.usecase.filmtransaction.FilmTransactionForCustomerNotFoundException
import com.casumo.videorental.usecase.filmtransaction.InMemorySelectedFilmView
import com.casumo.videorental.usecase.filmtransaction.SelectedFilmView
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test
import java.time.Instant
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class DefaultReplayFilmTransactionsUseCaseShould {
    lateinit var selectedFilmView: SelectedFilmView
    lateinit var customerRepository: CustomerRepository
    lateinit var filmTransactionRepository: FilmTransactionRepository

    @Before
    fun setup() {
        selectedFilmView = InMemorySelectedFilmView(ConcurrentHashMap())
        customerRepository = DummyCustomerRepository(mutableListOf())
        filmTransactionRepository = DummyFilmTransactionRepository(mutableListOf())
    }

    @Test
    fun restore_read_model_from_repository() {
        val customerId = CustomerId(UUID.randomUUID().toString())
        val secondCustomerId = CustomerId(UUID.randomUUID().toString())
        val filmId = aRandomFilmId()

        customerRepository.add(Customer.aNewCustomer(customerId, "Pedro"))
        customerRepository.add(Customer.aNewCustomer(secondCustomerId, "Juan"))
        filmTransactionRepository.save(FilmTransaction(filmTransactionId = aRandomFilmTransactionId(),
                customerId = customerId,
                filmId = filmId,
                numDays = 3,
                action = FilmActionEnum.RENT,
                createdOn = Instant.now()))

        filmTransactionRepository.save(FilmTransaction(filmTransactionId = aRandomFilmTransactionId(),
                customerId = customerId,
                filmId = aRandomFilmId(),
                numDays = 4,
                action = FilmActionEnum.RENT,
                createdOn = Instant.now().plusSeconds(80)))
        filmTransactionRepository.save(FilmTransaction(filmTransactionId = aRandomFilmTransactionId(),
                customerId = customerId,
                filmId = filmId,
                numDays = 0,
                action = FilmActionEnum.RETURN,
                createdOn = Instant.now().plusSeconds(8000)))

        filmTransactionRepository.save(FilmTransaction(filmTransactionId = aRandomFilmTransactionId(),
                customerId = secondCustomerId,
                filmId = filmId,
                numDays = 4,
                action = FilmActionEnum.RENT,
                createdOn = Instant.now().plusSeconds(9000)))

        val replayFilmTransactionsUseCase = DefaultReplayFilmTransactionsUseCase(selectedFilmView, customerRepository, filmTransactionRepository)

        replayFilmTransactionsUseCase.execute()

        Assertions.assertThat(selectedFilmView.isAlreadyRentedByCustomer(secondCustomerId, filmId)).isTrue()
    }

    @Test(expected = FilmTransactionForCustomerNotFoundException::class)
    fun throw_exception_when_restoring_data_in_wrong_order() {
        val customerId = CustomerId(UUID.randomUUID().toString())
        val filmId = aRandomFilmId()

        customerRepository.add(Customer.aNewCustomer(customerId, "Pedro"))

        filmTransactionRepository.save(FilmTransaction(filmTransactionId = aRandomFilmTransactionId(),
                customerId = customerId,
                filmId = filmId,
                numDays = 0,
                action = FilmActionEnum.RETURN,
                createdOn = Instant.now()))
        filmTransactionRepository.save(FilmTransaction(filmTransactionId = aRandomFilmTransactionId(),
                customerId = customerId,
                filmId = filmId,
                numDays = 4,
                action = FilmActionEnum.RENT,
                createdOn = Instant.now().plusSeconds(8000)))

        val replayFilmTransactionsUseCase = DefaultReplayFilmTransactionsUseCase(selectedFilmView, customerRepository, filmTransactionRepository)
        replayFilmTransactionsUseCase.execute()

        Assertions.assertThat(selectedFilmView.isAlreadyRentedByCustomer(customerId, filmId))
    }

    private fun aRandomFilmTransactionId(): FilmTransactionId {
        return FilmTransactionId(UUID.randomUUID().toString())
    }

    private fun aRandomFilmId(): FilmId {
        return FilmId(UUID.randomUUID().toString())
    }

}