package com.casumo.videorental.usecase.filmtransaction

import com.casumo.videorental.domain.model.customer.CustomerId
import com.casumo.videorental.domain.model.customer.CustomerRepository
import com.casumo.videorental.domain.model.film.FilmRepository
import com.casumo.videorental.domain.model.filmtransaction.FilmReturned
import com.casumo.videorental.domain.model.filmtransaction.FilmTransactionRepository
import com.casumo.videorental.usecase.filmtransaction.command.OneFilmReturnCommand
import java.time.Instant

class FilmReturnAssembler(private val filmRepository: FilmRepository,
                          private val filmTransactionRepository: FilmTransactionRepository,
                          private val customerRepository: CustomerRepository) {

    fun toFilmReturnEvent(customerId: CustomerId, filmReturnCommand: OneFilmReturnCommand): FilmReturned {
        val customer = customerRepository.findByOrFail(customerId)
        val transactionId = filmTransactionRepository.nextIdentity()
        val film = filmRepository.findByOrFail(filmReturnCommand.filmId)

        return FilmReturned(
                filmTransactionId = transactionId,
                customerId = customer.customerId,
                filmId = film.filmId,
                createdOn = Instant.now())

    }
}