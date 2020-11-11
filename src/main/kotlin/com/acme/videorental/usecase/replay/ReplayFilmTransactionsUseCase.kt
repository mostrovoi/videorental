package com.acme.videorental.usecase.replay

import com.acme.videorental.domain.model.customer.CustomerRepository
import com.acme.videorental.domain.model.filmtransaction.FilmActionEnum
import com.acme.videorental.domain.model.filmtransaction.FilmRented
import com.acme.videorental.domain.model.filmtransaction.FilmReturned
import com.acme.videorental.domain.model.filmtransaction.FilmTransactionRepository
import com.acme.videorental.usecase.filmtransaction.SelectedFilmView

class ReplayFilmTransactionsUseCase(val selectedFilmView: SelectedFilmView, val customerRepository: CustomerRepository, val filmTransactionRepository: FilmTransactionRepository)  {

    fun execute() {
        customerRepository.getAll().forEach { customer ->

            val filmsByUser = filmTransactionRepository.findBy(customerId = customer.customerId)

            filmsByUser.forEach { filmTransactionEvent ->
                if (filmTransactionEvent.action == FilmActionEnum.RENT) {
                    val filmRentEvent = FilmRented(filmTransactionId = filmTransactionEvent.filmTransactionId,
                            filmId = filmTransactionEvent.filmId,
                            customerId = filmTransactionEvent.customerId,
                            numDays = filmTransactionEvent.numDays,
                            createdOn = filmTransactionEvent.createdOn)
                    selectedFilmView.rentFilm(filmRentEvent)
                } else if (filmTransactionEvent.action == FilmActionEnum.RETURN) {
                    val filmReturnedEvent = FilmReturned(filmTransactionId = filmTransactionEvent.filmTransactionId,
                            filmId = filmTransactionEvent.filmId,
                            customerId = filmTransactionEvent.customerId,
                            createdOn = filmTransactionEvent.createdOn)
                    selectedFilmView.returnFilm(filmReturnedEvent)
                }

            }
        }

    }

}