package com.casumo.videorental.usecase.filmtransaction

import com.casumo.videorental.domain.model.customer.CustomerId
import com.casumo.videorental.domain.model.film.FilmId
import com.casumo.videorental.domain.model.filmtransaction.FilmRented
import com.casumo.videorental.domain.model.filmtransaction.FilmReturned

interface SelectedFilmView {
    fun isEmpty(): Boolean
    fun rentFilm(filmRented: FilmRented): Map<FilmId, RentedBy>
    fun returnFilm(filmReturned: FilmReturned): Map<FilmId, RentedBy>
    fun isAvailable(filmId: FilmId): Boolean
    fun getRentalDetailsFromFilm(filmId: FilmId): RentedBy
    fun isAlreadyRentedByCustomer(customerId: CustomerId, filmId: FilmId): Boolean
}