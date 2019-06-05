package com.casumo.videorental.usecase.filmtransaction.command

import com.casumo.videorental.domain.model.film.FilmId

class OneFilmRentalCommand(val filmId: FilmId, val numDays: Int)
