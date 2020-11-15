package com.acme.videorental.rentals.application

import com.acme.videorental.customers.domain.CustomerRepository
import com.acme.videorental.purchases.domain.FilmRepository
import com.acme.videorental.rentals.application.command.OneFilmReturnCommand
import com.acme.videorental.rentals.domain.FilmReturned
import com.acme.videorental.rentals.domain.RentalRepository
import com.acme.videorental.sharedKernel.domain.CustomerId
import java.time.Instant

class FilmReturnAssembler(private val filmRepository: FilmRepository,
                          private val rentalRepository: RentalRepository,
                          private val customerRepository: CustomerRepository) {

    fun toFilmReturnEvent(customerId: CustomerId, filmReturnCommand: OneFilmReturnCommand): FilmReturned {
        val customer = customerRepository.findByOrFail(customerId)
        val rentalId = rentalRepository.nextIdentity()
        val film = filmRepository.findByOrFail(filmReturnCommand.filmId)

        return FilmReturned(
                rentalId = rentalId,
                customerId = customer.customerId,
                filmId = film.filmId,
                createdOn = Instant.now())

    }
}