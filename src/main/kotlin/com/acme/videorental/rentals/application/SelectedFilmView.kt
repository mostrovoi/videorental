package com.acme.videorental.rentals.application

import com.acme.videorental.rentals.domain.FilmRented
import com.acme.videorental.rentals.domain.FilmReturned
import com.acme.videorental.sharedKernel.domain.CustomerId
import com.acme.videorental.sharedKernel.domain.FilmId

interface SelectedFilmView {
    fun isEmpty(): Boolean
    fun rentFilm(filmRented: FilmRented): Map<FilmId, RentedBy>
    fun returnFilm(filmReturned: FilmReturned): Map<FilmId, RentedBy>
    fun isAvailable(filmId: FilmId): Boolean
    fun getRentalDetailsFromFilm(filmId: FilmId): RentedBy
    fun isAlreadyRentedByCustomer(customerId: CustomerId, filmId: FilmId): Boolean
}