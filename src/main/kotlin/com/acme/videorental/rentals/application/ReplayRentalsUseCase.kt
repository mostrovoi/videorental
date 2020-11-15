package com.acme.videorental.rentals.application

import com.acme.videorental.customers.domain.CustomerRepository
import com.acme.videorental.rentals.domain.FilmRented
import com.acme.videorental.rentals.domain.FilmReturned
import com.acme.videorental.rentals.domain.RentalRepository
import com.acme.videorental.sharedKernel.domain.FilmActionEnum

class ReplayRentalsUseCase(val selectedFilmView: SelectedFilmView, val customerRepository: CustomerRepository, val rentalRepository: RentalRepository)  {

    fun execute() {
        customerRepository.getAll().forEach { customer ->

            val filmsByUser = rentalRepository.findBy(customerId = customer.customerId)

            filmsByUser.forEach { rentalEvent ->
                if (rentalEvent.action == FilmActionEnum.RENT) {
                    val filmRentEvent = FilmRented(rentalId = rentalEvent.rentalId,
                            filmId = rentalEvent.filmId,
                            customerId = rentalEvent.customerId,
                            numDays = rentalEvent.numDays,
                            createdOn = rentalEvent.createdOn)
                    selectedFilmView.rentFilm(filmRentEvent)
                } else if (rentalEvent.action == FilmActionEnum.RETURN) {
                    val filmReturnedEvent = FilmReturned(rentalId = rentalEvent.rentalId,
                            filmId = rentalEvent.filmId,
                            customerId = rentalEvent.customerId,
                            createdOn = rentalEvent.createdOn)
                    selectedFilmView.returnFilm(filmReturnedEvent)
                }

            }
        }

    }

}