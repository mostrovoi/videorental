package com.acme.videorental.rentals.application

import com.acme.videorental.sharedKernel.domain.FilmId

class RentalNotFoundException(filmId: FilmId) : Exception(String.format("Film with Id %s is not rented", filmId))