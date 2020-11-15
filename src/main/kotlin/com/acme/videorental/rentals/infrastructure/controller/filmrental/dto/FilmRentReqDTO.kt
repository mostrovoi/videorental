package com.acme.videorental.rentals.infrastructure.controller.filmrental.dto

import com.acme.videorental.sharedKernel.domain.FilmId

data class FilmRentReqDTO(val filmId: FilmId, val numDays: Int)
