package com.acme.videorental.usecase.filmtransaction

import com.acme.videorental.domain.model.film.FilmId

class FilmTransactionNotFoundExcepton(filmId: FilmId) : Exception(String.format("Film with Id %s is not rented", filmId))