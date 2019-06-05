package com.casumo.videorental.usecase.filmtransaction

import com.casumo.videorental.domain.model.film.FilmId

class FilmTransactionNotFoundExcepton(filmId: FilmId) : Exception(String.format("Film with Id %s is not rented", filmId))