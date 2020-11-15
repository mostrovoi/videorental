package com.acme.videorental.rentals.application

import com.acme.videorental.sharedKernel.domain.FilmId

class FilmAlreadyRentedException(filmId: FilmId) : Exception(String.format("Film with id %s is already rented", filmId))