package com.acme.videorental.rentals.application.command

import com.acme.videorental.sharedKernel.domain.FilmId

class OneFilmRentalCommand(val filmId: FilmId, val numDays: Int)
