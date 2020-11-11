package com.acme.videorental.usecase.filmtransaction.command

import com.acme.videorental.domain.model.film.FilmId

class OneFilmRentalCommand(val filmId: FilmId, val numDays: Int)
