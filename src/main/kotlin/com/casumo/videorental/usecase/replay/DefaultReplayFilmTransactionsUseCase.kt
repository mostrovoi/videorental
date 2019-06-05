package com.casumo.videorental.usecase.replay

import com.casumo.videorental.domain.model.customer.CustomerRepository
import com.casumo.videorental.domain.model.filmtransaction.FilmActionEnum
import com.casumo.videorental.domain.model.filmtransaction.FilmRented
import com.casumo.videorental.domain.model.filmtransaction.FilmReturned
import com.casumo.videorental.domain.model.filmtransaction.FilmTransactionRepository
import com.casumo.videorental.usecase.filmtransaction.SelectedFilmView

class DefaultReplayFilmTransactionsUseCase(val selectedFilmView: SelectedFilmView, val customerRepository: CustomerRepository, val filmTransactionRepository: FilmTransactionRepository) : ReplayFilmTransactionsUseCase {

    override fun execute() {
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