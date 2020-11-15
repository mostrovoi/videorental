package com.acme.videorental.rentals.application

import com.acme.videorental.customers.domain.CustomerRepository
import com.acme.videorental.purchases.domain.FilmRepository
import com.acme.videorental.rentals.application.command.OneFilmRentalCommand
import com.acme.videorental.rentals.domain.FilmRented
import com.acme.videorental.rentals.domain.RentalRepository
import com.acme.videorental.sharedKernel.domain.CustomerId
import java.time.Instant

class FilmRentalAssembler(private val filmRepository: FilmRepository,
                          private val rentalRepository: RentalRepository,
                          private val customerRepository: CustomerRepository) {

    fun toFilmRentedEvent(customerId: CustomerId, filmRentalCommand: OneFilmRentalCommand): FilmRented {
        val customer = customerRepository.findByOrFail(customerId)
        val rentalId = rentalRepository.nextIdentity()
        val film = filmRepository.findByOrFail(filmRentalCommand.filmId)

        //TODO: Use factory and event
        return FilmRented(
                rentalId = rentalId,
                customerId = customer.customerId,
                filmId = film.filmId,
                numDays = filmRentalCommand.numDays,
                createdOn = Instant.now())

    }
}