package com.acme.videorental.usecase.filmtransaction

import com.acme.videorental.domain.model.customer.CustomerId
import com.acme.videorental.domain.model.customer.CustomerRepository
import com.acme.videorental.domain.model.film.FilmRepository
import com.acme.videorental.domain.model.filmtransaction.FilmRented
import com.acme.videorental.domain.model.filmtransaction.FilmTransactionRepository
import com.acme.videorental.usecase.filmtransaction.command.OneFilmRentalCommand
import java.time.Instant

class FilmRentalAssembler(private val filmRepository: FilmRepository,
                          private val filmTransactionRepository: FilmTransactionRepository,
                          private val customerRepository: CustomerRepository) {

    fun toFilmRentedEvent(customerId: CustomerId, filmRentalCommand: OneFilmRentalCommand): FilmRented {
        val customer = customerRepository.findByOrFail(customerId)
        val transactionId = filmTransactionRepository.nextIdentity()
        val film = filmRepository.findByOrFail(filmRentalCommand.filmId)

        //TODO: Use factory and event
        return FilmRented(
                filmTransactionId = transactionId,
                customerId = customer.customerId,
                filmId = film.filmId,
                numDays = filmRentalCommand.numDays,
                createdOn = Instant.now())

    }
}