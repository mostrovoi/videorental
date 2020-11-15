package com.acme.videorental.rentals.application

import com.acme.videorental.customers.domain.Customer
import com.acme.videorental.customers.domain.CustomerRepository
import com.acme.videorental.customers.infrastructure.DummyCustomerRepository
import com.acme.videorental.rentals.domain.Rental
import com.acme.videorental.rentals.domain.RentalId
import com.acme.videorental.rentals.domain.RentalRepository
import com.acme.videorental.rentals.infrastructure.DummyRentalRepository
import com.acme.videorental.sharedKernel.domain.CustomerId
import com.acme.videorental.sharedKernel.domain.FilmActionEnum
import com.acme.videorental.sharedKernel.domain.FilmId
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test
import java.time.Instant
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class ReplayRentalsUseCaseShould {
    lateinit var selectedFilmView: SelectedFilmView
    lateinit var customerRepository: CustomerRepository
    lateinit var rentalRepository: RentalRepository

    @Before
    fun setup() {
        selectedFilmView = InMemorySelectedFilmView(ConcurrentHashMap())
        customerRepository = DummyCustomerRepository(mutableListOf())
        rentalRepository = DummyRentalRepository(mutableListOf())
    }

    @Test
    fun restore_read_model_from_repository() {
        val customerId = CustomerId(UUID.randomUUID().toString())
        val secondCustomerId = CustomerId(UUID.randomUUID().toString())
        val filmId = aRandomFilmId()

        customerRepository.add(Customer.aNewCustomer(customerId, "Pedro"))
        customerRepository.add(Customer.aNewCustomer(secondCustomerId, "Juan"))
        rentalRepository.save(Rental(rentalId = aRandomRentalId(),
                customerId = customerId,
                filmId = filmId,
                numDays = 3,
                action = FilmActionEnum.RENT,
                createdOn = Instant.now()))

        rentalRepository.save(Rental(rentalId = aRandomRentalId(),
                customerId = customerId,
                filmId = aRandomFilmId(),
                numDays = 4,
                action = FilmActionEnum.RENT,
                createdOn = Instant.now().plusSeconds(80)))
        rentalRepository.save(Rental(rentalId = aRandomRentalId(),
                customerId = customerId,
                filmId = filmId,
                numDays = 0,
                action = FilmActionEnum.RETURN,
                createdOn = Instant.now().plusSeconds(8000)))

        rentalRepository.save(Rental(rentalId = aRandomRentalId(),
                customerId = secondCustomerId,
                filmId = filmId,
                numDays = 4,
                action = FilmActionEnum.RENT,
                createdOn = Instant.now().plusSeconds(9000)))

        val replayRentalsUseCase = ReplayRentalsUseCase(selectedFilmView, customerRepository, rentalRepository)

        replayRentalsUseCase.execute()

        Assertions.assertThat(selectedFilmView.isAlreadyRentedByCustomer(secondCustomerId, filmId)).isTrue()
    }

    @Test(expected = RentalForCustomerNotFoundException::class)
    fun throw_exception_when_restoring_data_in_wrong_order() {
        val customerId = CustomerId(UUID.randomUUID().toString())
        val filmId = aRandomFilmId()

        customerRepository.add(Customer.aNewCustomer(customerId, "Pedro"))

        rentalRepository.save(Rental(rentalId = aRandomRentalId(),
                customerId = customerId,
                filmId = filmId,
                numDays = 0,
                action = FilmActionEnum.RETURN,
                createdOn = Instant.now()))
        rentalRepository.save(Rental(rentalId = aRandomRentalId(),
                customerId = customerId,
                filmId = filmId,
                numDays = 4,
                action = FilmActionEnum.RENT,
                createdOn = Instant.now().plusSeconds(8000)))

        val replayRentalsUseCase = ReplayRentalsUseCase(selectedFilmView, customerRepository, rentalRepository)
        replayRentalsUseCase.execute()

        Assertions.assertThat(selectedFilmView.isAlreadyRentedByCustomer(customerId, filmId))
    }

    private fun aRandomRentalId(): RentalId {
        return RentalId(UUID.randomUUID().toString())
    }

    private fun aRandomFilmId(): FilmId {
        return FilmId(UUID.randomUUID().toString())
    }

}