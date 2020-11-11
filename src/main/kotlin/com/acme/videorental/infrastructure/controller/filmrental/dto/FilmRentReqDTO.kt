package com.acme.videorental.infrastructure.controller.filmrental.dto

import com.acme.videorental.domain.model.film.FilmId

data class FilmRentReqDTO(val filmId: FilmId, val numDays: Int)
