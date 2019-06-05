package com.casumo.videorental.usecase.filmtransaction

import com.casumo.videorental.domain.model.film.FilmId

class FilmAlreadyRentedException(filmId: FilmId) : Exception(String.format("Film with id %s is already rented", filmId))